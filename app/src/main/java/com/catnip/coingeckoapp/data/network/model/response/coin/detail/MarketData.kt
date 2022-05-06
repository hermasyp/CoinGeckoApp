package com.catnip.coingeckoapp.data.network.model.response.coin.detail


import com.catnip.coingeckoapp.data.network.model.response.coin.detail.CurrentPrice
import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("current_price")
    var currentPrice: CurrentPrice? = null
)