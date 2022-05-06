package com.catnip.coingeckoapp.data.network.datasource.coin

import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import kotlinx.coroutines.flow.Flow

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface CoinGeckoDataSource {
    suspend fun getCoinList(): Flow<List<Coin>>
    suspend fun getCoinDetail(coinID: String): Flow<CoinDetailResponse>
}