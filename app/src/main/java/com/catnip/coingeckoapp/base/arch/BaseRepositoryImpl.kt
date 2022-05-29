package com.catnip.coingeckoapp.base.arch

import android.util.Log
import com.catnip.coingeckoapp.BuildConfig
import com.catnip.coingeckoapp.base.exception.ApiErrorException
import com.catnip.coingeckoapp.base.exception.NoInternetConnectionException
import com.catnip.coingeckoapp.base.exception.UnexpectedApiErrorException
import com.catnip.coingeckoapp.base.wrapper.NetworkResource
import retrofit2.HttpException
import java.io.IOException

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
open class BaseRepositoryImpl : BaseContract.BaseRepository {
    override fun logResponse(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(BaseRepositoryImpl::class.java.simpleName, msg.orEmpty())
        }
    }

    suspend fun <T> call(apiCall: suspend () -> T): NetworkResource<T> {
        return try {
            NetworkResource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> NetworkResource.Error(NoInternetConnectionException())
                is HttpException -> {
                    when (val code = throwable.code()) {
                        in 300..308 -> {
                            NetworkResource.Error(ApiErrorException("Redirect", code))
                        }
                        in 400..451 -> {
                            NetworkResource.Error(ApiErrorException("Client Error", code))
                        }
                        in 500..511 -> {
                            NetworkResource.Error(ApiErrorException("Server Error", code))
                        }
                        else -> {
                            NetworkResource.Error(ApiErrorException(httpCode = code))
                        }
                    }
                }
                else -> {
                    NetworkResource.Error(UnexpectedApiErrorException())
                }
            }
        }
    }
}