package com.ajeeb.kart.main.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val itemID: String,
    val itemName: String,
    val sellingPrice: Double,
    val taxPercentage: Double,
    val quantity: Int,
    val lastUpdated: Long
)
