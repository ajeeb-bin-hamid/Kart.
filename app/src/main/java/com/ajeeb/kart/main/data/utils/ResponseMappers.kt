package com.ajeeb.kart.main.data.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.ajeeb.kart.common.data.model.CartTableItem
import com.ajeeb.kart.main.data.model.GetProductsResponse
import com.ajeeb.kart.main.domain.model.CartItem
import com.ajeeb.kart.main.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun mapToProducts(rawResponse: ArrayList<GetProductsResponse>): ArrayList<Product> {
    return rawResponse
        .mapNotNull { response ->
            val id = response.itemID
            val name = response.itemName
            val price = response.sellingPrice
            val tax = response.taxPercentage

            if (id != null && name != null && price != null && tax != null) {
                Product(
                    itemID = id,
                    itemName = name,
                    sellingPrice = price,
                    taxPercentage = tax
                )
            } else {
                null // skip incomplete items
            }
        }
        .toCollection(ArrayList())
}

fun mapToCartItemsFlow(rawFlow: Flow<List<CartTableItem>>): Flow<SnapshotStateList<CartItem>> {
    return rawFlow.map { response ->
        mutableStateListOf<CartItem>().apply {
            response.forEach {
                add(
                    CartItem(
                        itemID = it.itemID,
                        itemName = it.itemName,
                        sellingPrice = it.sellingPrice,
                        taxPercentage = it.taxPercentage,
                        quantity = it.quantity,
                        lastUpdated = it.lastUpdated
                    )
                )
            }
        }
    }
}