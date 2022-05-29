package com.catnip.coingeckoapp.ui.feature.coinlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.coingeckoapp.base.wrapper.ViewResource
import com.catnip.coingeckoapp.domain.GetCoinListUseCase
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinViewParam
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinListViewModel(val getCoinListUseCase: GetCoinListUseCase) : ViewModel() {

    var coinList = MutableLiveData<ViewResource<List<CoinViewParam>>>()

    fun getList() {
        viewModelScope.launch {
            getCoinListUseCase().collect{
                coinList.value = it
            }
        }
    }
}