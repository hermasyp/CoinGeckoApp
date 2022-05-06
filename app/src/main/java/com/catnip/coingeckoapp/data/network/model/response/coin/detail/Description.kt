package com.catnip.coingeckoapp.data.network.model.response.coin.detail


import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("en")
    var en: String? = null
)