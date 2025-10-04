package com.ajeeb.kart.main.data.utils

import com.ajeeb.kart.main.data.model.GetProductsResponse
import com.ajeeb.kart.main.domain.model.Product

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
