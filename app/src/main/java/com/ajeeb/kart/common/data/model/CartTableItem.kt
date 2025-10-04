package com.ajeeb.kart.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ajeeb.kart.common.data.utils.CART_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = CART_TABLE)
@Serializable
data class CartTableItem(
    @PrimaryKey(autoGenerate = false) val itemID: String,
    val itemName: String,
    val sellingPrice: Double,
    val taxPercentage: Double,
    val quantity: Int,
    val lastUpdated: Long
)
