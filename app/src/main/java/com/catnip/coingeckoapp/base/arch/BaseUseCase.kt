package com.catnip.coingeckoapp.base.arch

import com.catnip.coingeckoapp.base.exception.UnexpectedApiErrorException
import com.catnip.coingeckoapp.base.wrapper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
abstract class BaseUseCase<P,R : Any> constructor(private val dispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(param: P): Flow<Resource<R>> {
        return execute(param)
            .catch { emit(Resource.Error(UnexpectedApiErrorException())) }
            .flowOn(dispatcher)
    }

    @Throws(RuntimeException::class)
    abstract suspend fun execute(param: P): Flow<Resource<R>>

}