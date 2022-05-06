package com.catnip.coingeckoapp.domain

import com.catnip.coingeckoapp.base.arch.BaseUseCase
import com.catnip.coingeckoapp.base.wrapper.Resource
import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetCoinListUseCase(dispatcher: CoroutineDispatcher) :
    BaseUseCase<Unit, Resource<List<Coin>>>(dispatcher) {

    override fun execute(param: Unit): Flow<Resource<Resource<List<Coin>>>> {
        TODO("Not yet implemented")
    }

}