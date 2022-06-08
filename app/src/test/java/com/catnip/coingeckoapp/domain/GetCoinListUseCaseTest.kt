package com.catnip.coingeckoapp.domain

import com.catnip.coingeckoapp.base.exception.ApiErrorException
import com.catnip.coingeckoapp.base.wrapper.NetworkResource
import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import com.catnip.coingeckoapp.data.repository.CoinRepository
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinViewParam
import com.catnip.coingeckoapp.ui.viewparams.coin.mapToViewParam
import com.catnip.coingeckoapp.ui.viewparams.coin.mapToViewParams
import com.catnip.coingeckoapp.utils.test
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.spyk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Written with love by Muhammad Hermas Yuda Pamungkas
 * Github : https://github.com/hermasyp
 */
class GetCoinListUseCaseTest{
    @MockK
    lateinit var repository: CoinRepository

    private lateinit var useCase: GetCoinListUseCase

    init {
        MockKAnnotations.init(this)
    }


    @Before
    fun before() {
        useCase = spyk(GetCoinListUseCase(repository, Dispatchers.IO))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun execute_success() {
        runTest {
            val data = mutableListOf<Coin>().apply {
                add(Coin("","","","",""))
            }
            val returnData = mutableListOf<CoinViewParam>().apply {
                add(CoinViewParam("","","","",""))
            }
            mockkStatic(List<Coin>::mapToViewParams)
            every { data.mapToViewParams() } returns returnData
            every { repository.getCoins(any()) } returns flowOf(NetworkResource.Success(data))
            useCase.execute().test(this)
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
            every { repository.getCoins(any()) } returns flowOf(NetworkResource.Error(exception))
            useCase.execute().test(this)
                .satisfies { value ->
                    kotlin.test.assertEquals(value.exception, exception)
                }
                .finish()
        }
    }
}