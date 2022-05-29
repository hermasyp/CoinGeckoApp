package com.catnip.coingeckoapp.data.network.datasource.coin

import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import com.catnip.coingeckoapp.data.network.services.CoinGeckoApiServices


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinGeckoDataSourceImpl(private val coinGeckoApiServices: CoinGeckoApiServices) : CoinGeckoDataSource {
    override suspend fun getCoinList(): List<Coin> {
        return coinGeckoApiServices.getCoinList()
    }

    override suspend fun getCoinDetail(coinID: String): CoinDetailResponse {
        return coinGeckoApiServices.getCoinDetail(coinID)
    }

}