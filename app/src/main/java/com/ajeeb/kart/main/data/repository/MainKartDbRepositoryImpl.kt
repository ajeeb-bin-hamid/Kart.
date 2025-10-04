package com.ajeeb.kart.main.data.repository

import com.ajeeb.kart.common.data.model.CartTableItem
import com.ajeeb.kart.common.domain.utils.Issues
import com.ajeeb.kart.common.domain.utils.Result
import com.ajeeb.kart.main.data.db.MainKartDbDao
import com.ajeeb.kart.main.data.utils.mapToCartItemsFlow
import com.ajeeb.kart.main.domain.model.CartItem
import com.ajeeb.kart.main.domain.model.Product
import com.ajeeb.kart.main.domain.repository.MainKartDbRepository
import kotlinx.coroutines.flow.Flow

class MainKartDbRepositoryImpl(
    private val mainKartDbDao: MainKartDbDao
) : MainKartDbRepository {

    override fun getAllCartItems(): Flow<List<CartItem>> {
        val rawFlow = mainKartDbDao.getAllCartItems()
        val formattedFlow = mapToCartItemsFlow(rawFlow)
        return formattedFlow
    }

    override suspend fun addItemToCart(product: Product): Result<Unit, Issues.Database> {

        return try {
            val existingItem = mainKartDbDao.getCartItemById(product.itemID)
            if (existingItem != null) {
                mainKartDbDao.insertCartItem(
                    existingItem.copy(
                        quantity = existingItem.quantity + 1,
                        lastUpdated = System.currentTimeMillis()
                    )
                )
            } else {
                val cartTableItem = CartTableItem(
                    itemID = product.itemID,
                    itemName = product.itemName,
                    sellingPrice = product.sellingPrice,
                    taxPercentage = product.taxPercentage,
                    quantity = 1,
                    lastUpdated = System.currentTimeMillis()
                )
                mainKartDbDao.insertCartItem(cartTableItem)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Issues.Database.Unknown)
        }
    }

    override suspend fun removeItemToCart(product: Product): Result<Unit, Issues.Database> {
        return try {
            val existingItem = mainKartDbDao.getCartItemById(product.itemID)
            if (existingItem != null) {
                if (existingItem.quantity > 1) {
                    mainKartDbDao.insertCartItem(
                        existingItem.copy(
                            quantity = existingItem.quantity - 1,
                            lastUpdated = System.currentTimeMillis()
                        )
                    )
                } else {
                    mainKartDbDao.deleteCartItemById(product.itemID)
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Issues.Database.Unknown)
        }
    }
}