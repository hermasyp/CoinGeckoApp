package com.catnip.coingeckoapp.ui.feature.detailcoin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.catnip.coingeckoapp.base.exception.ApiErrorException
import com.catnip.coingeckoapp.base.wrapper.ViewResource
import com.catnip.coingeckoapp.domain.GetCoinDetailUseCase
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinDetailViewParam
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Written with love by Muhammad Hermas Yuda Pamungkas
 * Github : https://github.com/hermasyp
 */

class CoinDetailViewModelTest{
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: GetCoinDetailUseCase

    @MockK(relaxed = true)
    private lateinit var observer : Observer<ViewResource<CoinDetailViewParam>>

    private lateinit var viewModel: CoinDetailViewModel

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun before(){
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = spyk(CoinDetailViewModel(useCase))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDetail_success_test(){
        runTest {
            val dataCoin = CoinDetailViewParam(listOf(),"","","","","","")
            val returnData = ViewResource.Success(dataCoin)
            coEvery { useCase(any()) } returns flowOf(returnData)
            viewModel.coinDetail.observeForever(observer)
            viewModel.getDetail("123")
            verify { observer.onChanged(returnData) }
            viewModel.coinDetail.removeObserver(observer)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDetail_error_test(){
        runTest {
            val exception = ApiErrorException()
            val returnData = ViewResource.Error<CoinDetailViewParam>(exception)
            coEvery { useCase(any()) } returns flowOf(returnData)
            viewModel.coinDetail.observeForever(observer)
            viewModel.getDetail("123")
            verify { observer.onChanged(returnData) }
            viewModel.coinDetail.removeObserver(observer)
        }
    }


}