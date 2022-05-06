package com.catnip.coingeckoapp.data.repository

import com.catnip.coingeckoapp.base.arch.BaseContract
import com.catnip.coingeckoapp.base.arch.BaseRepositoryImpl
import com.catnip.coingeckoapp.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

interface CoinRepository : BaseContract.BaseRepository{
    fun getCoins() : Flow<List<Coin>>
    fun getCoin(id : String) : Flow<CoinDetailResponse>
}
class CoinRepositoryImpl @Inject constructor(val dataSource: CoinGeckoDataSource) : CoinRepository, BaseRepositoryImpl(){
    override fun getCoins(): Flow<List<Coin>> {
        TODO("Not yet implemented")
    }

    override fun getCoin(id: String): Flow<CoinDetailResponse> {
        TODO("Not yet implemented")
    }
}