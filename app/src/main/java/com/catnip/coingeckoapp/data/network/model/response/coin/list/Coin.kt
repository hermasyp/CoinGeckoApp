package com.catnip.coingeckoapp.data.network.model.response.coin.list

import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CoinDetailResponse
import com.google.gson.annotations.SerializedName

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class Coin(
    @SerializedName("id")
    var id: String?,
    @SerializedName("symbol")
    var symbol: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("current_price")
    var currentPrice: String?,
)