package com.ajeeb.kart.main.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val itemID: String, val itemName: String, val sellingPrice: Double, val taxPercentage: Double
)
