package com.catnip.coingeckoapp.domain

import com.catnip.coingeckoapp.base.arch.BaseUseCase
import com.catnip.coingeckoapp.base.wrapper.NetworkResource
import com.catnip.coingeckoapp.base.wrapper.ViewResource
import com.catnip.coingeckoapp.data.repository.CoinRepository
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinViewParam
import com.catnip.coingeckoapp.ui.viewparams.coin.mapToViewParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetCoinListUseCase(
    val repository: CoinRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, List<CoinViewParam>>(dispatcher) {
    override suspend fun execute(param: Unit?): Flow<ViewResource<List<CoinViewParam>>> {
        return repository.getCoins(dispatcher).map {
            when(it){
                is NetworkResource.Success -> {
                    if(it.data?.isNotEmpty() == true){
                        ViewResource.Success(it.data.mapToViewParams())
                    }else{
                        ViewResource.Empty(it.data.mapToViewParams())
                    }
                }
                is NetworkResource.Loading -> {
                    ViewResource.Loading()
                }
                is NetworkResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }
    }
}