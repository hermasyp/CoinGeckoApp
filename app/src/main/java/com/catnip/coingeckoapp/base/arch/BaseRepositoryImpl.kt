package com.catnip.coingeckoapp.base.arch

import android.util.Log
import com.catnip.coingeckoapp.BuildConfig
import com.catnip.coingeckoapp.base.exception.ApiErrorException
import com.catnip.coingeckoapp.base.exception.NoInternetConnectionException
import com.catnip.coingeckoapp.base.exception.UnexpectedApiErrorException
import com.catnip.coingeckoapp.base.wrapper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
open class BaseRepositoryImpl : BaseContract.BaseRepository {
    override fun logResponse(msg: String?) {
        if(BuildConfig.DEBUG){
            Log.d(BaseRepositoryImpl::class.java.simpleName, msg.orEmpty())
        }
    }

    suspend fun <T> call(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Resource<T> {
        return withContext(dispatcher) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> Resource.Error(NoInternetConnectionException())
                    is HttpException -> {
                        when (val code = throwable.code()) {
                            in 300..308 -> {
                                Resource.Error(ApiErrorException("Redirect",code))
                            }
                            in 400..451 -> {
                                Resource.Error(ApiErrorException("Client Error",code))
                            }
                            in 500..511 -> {
                                Resource.Error(ApiErrorException("Server Error",code))
                            }
                            else -> {
                                Resource.Error(ApiErrorException(httpCode = code))
                            }
                        }
                    }
                    else -> {
                        Resource.Error(UnexpectedApiErrorException())
                    }
                }
            }
        }
    }
}