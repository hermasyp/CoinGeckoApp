package com.catnip.coingeckoapp.domain

import com.catnip.coingeckoapp.base.exception.ApiErrorException
import com.catnip.coingeckoapp.base.wrapper.NetworkResource
import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.coingeckoapp.data.repository.CoinRepository
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinDetailViewParam
import com.catnip.coingeckoapp.ui.viewparams.coin.mapToViewParam
import com.catnip.coingeckoapp.utils.test
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Written with love by Muhammad Hermas Yuda Pamungkas
 * Github : https://github.com/hermasyp
 */


class GetCoinDetailUseCaseTest {

    @MockK
    lateinit var repository: CoinRepository

    private lateinit var useCase: GetCoinDetailUseCase

    init {
        MockKAnnotations.init(this)
    }

    @Before
    fun before() {
        useCase = spyk(GetCoinDetailUseCase(repository, Dispatchers.IO))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun execute_success() {
        runTest {
            val data = CoinDetailResponse()
            val returnData = CoinDetailViewParam(listOf(),"","","","","","")

            mockkStatic(CoinDetailResponse::mapToViewParam)
            every { data.mapToViewParam() } returns returnData
            every { repository.getCoin(any(), any()) } returns flowOf(NetworkResource.Success(data))

            useCase.execute("123").test(this)
                .satisfies { value ->
                    assertEquals(value.data,returnData)
                }
                .finish()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun execute_failed() {
        runTest {
            val exception = ApiErrorException()
            mockkStatic(CoinDetailResponse::mapToViewParam)
            every { repository.getCoin(any(), any()) } returns flowOf(NetworkResource.Error(exception))
            useCase.execute("123").test(this)
                .satisfies { value ->
                    assertEquals(value.exception,exception)
                }
                .finish()
        }
    }

}