package com.catnip.coingeckoapp.ui.viewparams.coin

import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class CoinDetailViewParam(
    var categories: List<String>,
    var description: String,
    var id: String,
    var imageUrl: String,
    var currentPrice: String,
    var name: String,
    var symbol: String
)

fun CoinDetailResponse?.mapToViewParam() = CoinDetailViewParam(
    categories = this?.categories ?: listOf(),
    description = this?.description?.en.orEmpty(),
    id = this?.id.orEmpty(),
    imageUrl = this?.image?.large.orEmpty(),
    currentPrice = this?.marketData?.currentPrice?.usd.toString(),
    name = this?.name.orEmpty(),
    symbol = this?.symbol.orEmpty(),
)
