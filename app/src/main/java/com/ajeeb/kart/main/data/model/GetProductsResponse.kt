package com.ajeeb.kart.main.data.model

import com.google.gson.annotations.SerializedName

data class GetProductsResponse(
    @SerializedName("itemID") val itemID: String? = null,
    @SerializedName("itemName") val itemName: String? = null,
    @SerializedName("sellingPrice") val sellingPrice: Double? = null,
    @SerializedName("taxPercentage") val taxPercentage: Double? = null
)
