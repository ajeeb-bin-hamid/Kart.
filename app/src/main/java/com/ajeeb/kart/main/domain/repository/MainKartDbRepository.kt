package com.ajeeb.kart.main.domain.repository

import com.ajeeb.kart.common.domain.utils.Issues
import com.ajeeb.kart.common.domain.utils.Result
import com.ajeeb.kart.main.domain.model.CartItem
import com.ajeeb.kart.main.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface MainKartDbRepository {

    fun getAllCartItems(): Flow<List<CartItem>>

    suspend fun addItemToCart(product: Product): Result<Unit, Issues.Database>

    suspend fun removeItemToCart(product: Product): Result<Unit, Issues.Database>

}