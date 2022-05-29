package com.catnip.coingeckoapp.domain

import com.catnip.coingeckoapp.base.arch.BaseUseCase
import com.catnip.coingeckoapp.base.wrapper.NetworkResource
import com.catnip.coingeckoapp.base.wrapper.ViewResource
import com.catnip.coingeckoapp.data.repository.CoinRepository
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinDetailViewParam
import com.catnip.coingeckoapp.ui.viewparams.coin.mapToViewParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetCoinDetailUseCase(
    val repository: CoinRepository,
    val dispatcher: CoroutineDispatcher
) :
    BaseUseCase<String, CoinDetailViewParam>(dispatcher) {
    override suspend fun execute(param: String?): Flow<ViewResource<CoinDetailViewParam>> {
        return repository.getCoin(dispatcher, param.orEmpty()).map {
            when (it) {
                is NetworkResource.Success -> {
                    ViewResource.Success(it.data.mapToViewParam())
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