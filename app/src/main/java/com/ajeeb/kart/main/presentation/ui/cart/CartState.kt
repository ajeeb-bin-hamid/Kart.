package com.ajeeb.kart.main.presentation.ui.cart

import androidx.navigation.NavType
import com.ajeeb.kart.common.presentation.utils.navType
import com.ajeeb.kart.main.domain.model.CartItem
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Serializable
data class CartState(
    val cartItems: List<CartItem> = listOf(),
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf(
            typeOf<List<CartItem>>() to navType<List<CartItem>>()
        )
    }
}