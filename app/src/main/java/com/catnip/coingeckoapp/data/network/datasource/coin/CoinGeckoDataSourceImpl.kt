package com.catnip.coingeckoapp.data.network.datasource.coin

import com.catnip.coingeckoapp.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import com.catnip.coingeckoapp.data.network.services.CoinGeckoApiServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinGeckoDataSourceImpl
@Inject constructor(private val coinGeckoApiServices: CoinGeckoApiServices) : CoinGeckoDataSource {
    override suspend fun getCoinList(): Flow<List<Coin>> {
        return flow { coinGeckoApiServices.getCoinList() }
    }

    override suspend fun getCoinDetail(coinID: String): Flow<CoinDetailResponse> {
        return flow { coinGeckoApiServices.getCoinDetail(coinID) }
    }

}