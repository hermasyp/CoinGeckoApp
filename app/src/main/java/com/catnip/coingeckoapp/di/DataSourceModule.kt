package com.catnip.coingeckoapp.di

import com.catnip.coingeckoapp.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.coingeckoapp.data.network.datasource.coin.CoinGeckoDataSourceImpl
import com.catnip.coingeckoapp.data.network.services.CoinGeckoApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideCoinGeckoDataSource(coinGeckoApiServices: CoinGeckoApiServices): CoinGeckoDataSource {
        return CoinGeckoDataSourceImpl(coinGeckoApiServices)
    }
}