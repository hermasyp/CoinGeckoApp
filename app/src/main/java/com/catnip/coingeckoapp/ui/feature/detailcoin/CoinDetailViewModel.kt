package com.catnip.coingeckoapp.ui.feature.detailcoin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.coingeckoapp.base.wrapper.ViewResource
import com.catnip.coingeckoapp.domain.GetCoinDetailUseCase
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinDetailViewParam
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinDetailViewModel(val coinDetailUseCase: GetCoinDetailUseCase) : ViewModel() {

    var coinDetail = MutableLiveData<ViewResource<CoinDetailViewParam>>()

    fun getDetail(id : String) {
        viewModelScope.launch {
            coinDetailUseCase(id).collect{
                coinDetail.value = it
            }
        }
    }
}