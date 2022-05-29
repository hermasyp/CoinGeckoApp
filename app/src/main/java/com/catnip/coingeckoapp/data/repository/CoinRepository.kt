package com.catnip.coingeckoapp.data.repository

import com.catnip.coingeckoapp.base.arch.BaseContract
import com.catnip.coingeckoapp.base.arch.BaseRepositoryImpl
import com.catnip.coingeckoapp.base.wrapper.NetworkResource
import com.catnip.coingeckoapp.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

interface CoinRepository : BaseContract.BaseRepository {
    fun getCoins(dispatcher: CoroutineDispatcher): Flow<NetworkResource<List<Coin>>>
    fun getCoin(dispatcher: CoroutineDispatcher, id: String): Flow<NetworkResource<CoinDetailResponse>>
}

class CoinRepositoryImpl(val dataSource: CoinGeckoDataSource) : CoinRepository,
    BaseRepositoryImpl() {
    override fun getCoins(dispatcher: CoroutineDispatcher): Flow<NetworkResource<List<Coin>>> {
        return flow {
            emit(NetworkResource.Loading())
            emit(call { dataSource.getCoinList() })
        }.flowOn(dispatcher)
    }

    override fun getCoin(
        dispatcher: CoroutineDispatcher,
        id: String
    ): Flow<NetworkResource<CoinDetailResponse>> {
        return flow {
            emit(NetworkResource.Loading())
            emit(call { dataSource.getCoinDetail(id) })
        }.flowOn(dispatcher)
    }

}