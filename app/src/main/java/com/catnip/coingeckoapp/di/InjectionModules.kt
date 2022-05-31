package com.catnip.coingeckoapp.di

import com.catnip.coingeckoapp.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.coingeckoapp.data.network.datasource.coin.CoinGeckoDataSourceImpl
import com.catnip.coingeckoapp.data.network.services.CoinGeckoApiServices
import com.catnip.coingeckoapp.data.repository.CoinRepository
import com.catnip.coingeckoapp.data.repository.CoinRepositoryImpl
import com.catnip.coingeckoapp.domain.GetCoinDetailUseCase
import com.catnip.coingeckoapp.domain.GetCoinListUseCase
import com.catnip.coingeckoapp.ui.feature.coinlist.CoinListViewModel
import com.catnip.coingeckoapp.ui.feature.detailcoin.CoinDetailViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object InjectionModules {
    fun getModules() = listOf(network, dataSource, repository, useCases, viewModels)

    private val network = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { CoinGeckoApiServices.invoke(get()) }
    }
    private val dataSource = module {
        single<CoinGeckoDataSource> { CoinGeckoDataSourceImpl(get()) }
    }
    private val repository = module {
        single<CoinRepository> { CoinRepositoryImpl(get()) }
    }
    private val useCases = module {
        single { GetCoinDetailUseCase(get(), dispatcher = Dispatchers.IO) }
        single { GetCoinListUseCase(get(), dispatcher = Dispatchers.IO) }
    }
    private val viewModels = module {
        viewModel { CoinListViewModel(get()) }
        viewModel { CoinDetailViewModel(get()) }
    }
}