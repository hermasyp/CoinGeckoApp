package com.catnip.coingeckoapp.di

import android.content.Context
import com.catnip.coingeckoapp.data.network.services.CoinGeckoApiServices
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideCoinGeckoApiServices(chuckerInterceptor: ChuckerInterceptor): CoinGeckoApiServices {
        return CoinGeckoApiServices.invoke(chuckerInterceptor)
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }
}