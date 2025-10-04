package com.ajeeb.kart.main.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajeeb.kart.common.data.model.CartTableItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MainKartDbDao {

    @Query("SELECT * FROM cart ORDER BY itemID ASC")
    fun getAllCartItems(): Flow<List<CartTableItem>>

    @Query("SELECT * FROM cart WHERE itemID = :id LIMIT 1")
    suspend fun getCartItemById(id: String): CartTableItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartTableItem)

    @Query("DELETE FROM cart WHERE itemID = :id")
    suspend fun deleteCartItemById(id: String)

    @Query("DELETE FROM cart")
    suspend fun clearCart()
}
