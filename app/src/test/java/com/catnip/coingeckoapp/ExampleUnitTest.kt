package com.catnip.coingeckoapp

import com.catnip.coingeckoapp.base.exception.ApiErrorException
import com.catnip.coingeckoapp.base.exception.UnexpectedApiErrorException
import com.catnip.coingeckoapp.base.wrapper.NetworkResource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun addition_isCorrect() {

        runBlocking {
            val data = flow {
                emit(
                    try {
                        NetworkResource.Success("here")
                    } catch (e: Exception) {
                        NetworkResource.Error(e)
                    }
                )
            }.catch { cause ->
                emit(NetworkResource.Error(UnexpectedApiErrorException()))
            }
            assertEquals(NetworkResource.Error<String>(ApiErrorException()).exception is ApiErrorException, data.first().exception is ApiErrorException)
        }
    }
}