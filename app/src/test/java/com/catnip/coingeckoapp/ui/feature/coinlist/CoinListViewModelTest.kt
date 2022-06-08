package com.catnip.coingeckoapp.ui.feature.coinlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.catnip.coingeckoapp.base.exception.ApiErrorException
import com.catnip.coingeckoapp.base.wrapper.ViewResource
import com.catnip.coingeckoapp.domain.GetCoinListUseCase
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinViewParam
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
class CoinListViewModelTest{

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: GetCoinListUseCase

    @MockK(relaxed = true)
    private lateinit var observer : Observer<ViewResource<List<CoinViewParam>>>

    private lateinit var viewModel: CoinListViewModel

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun before(){
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = spyk(CoinListViewModel(useCase))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCoinList_success_test(){
        runTest {
            val data = mutableListOf<CoinViewParam>().apply {
                add(CoinViewParam("", "", "", "", ""))
            }.toList()
            val returnData = ViewResource.Success(data)
            coEvery { useCase() } returns flowOf(returnData)
            viewModel.coinList.observeForever(observer)
            viewModel.getList()
            verify { observer.onChanged(returnData) }
            viewModel.coinList.removeObserver(observer)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCoinList_error_test(){
        runTest {
            val exception = ApiErrorException()
            val returnData = ViewResource.Error<List<CoinViewParam>>(exception)
            coEvery { useCase() } returns flowOf(returnData)
            viewModel.coinList.observeForever(observer)
            viewModel.getList()
            verify { observer.onChanged(returnData) }
            viewModel.coinList.removeObserver(observer)
        }
    }


}