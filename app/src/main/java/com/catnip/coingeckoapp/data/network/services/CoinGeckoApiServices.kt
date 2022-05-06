package com.catnip.coingeckoapp.data.network.services

import com.catnip.coingeckoapp.BuildConfig
import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface CoinGeckoApiServices {

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&sparkline=false")
    suspend fun getCoinList(): List<Coin>

    @GET("coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
    suspend fun getCoinDetail(@Path("id") id : String): CoinDetailResponse

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor : ChuckerInterceptor) : CoinGeckoApiServices {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_COIN_GECKO)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(CoinGeckoApiServices::class.java)
        }
    }
}