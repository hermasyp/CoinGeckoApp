package com.catnip.coingeckoapp.ui.viewparams.coin

import com.catnip.coingeckoapp.data.network.model.response.coin.list.Coin
import com.google.gson.annotations.SerializedName

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class CoinViewParam(
    var id: String,
    var symbol: String,
    var name: String,
    var image: String,
    var currentPrice: String
)

fun Coin?.mapToViewParam() = CoinViewParam(
    id = this?.id.orEmpty(),
    symbol = this?.symbol.orEmpty(),
    name = this?.name.orEmpty(),
    image = this?.image.orEmpty(),
    currentPrice = this?.currentPrice.orEmpty(),
)

fun List<Coin>?.mapToViewParams() = mutableListOf<CoinViewParam>().apply {
    addAll(this@mapToViewParams?.map {
        it.mapToViewParam()
    } ?: listOf())
}
