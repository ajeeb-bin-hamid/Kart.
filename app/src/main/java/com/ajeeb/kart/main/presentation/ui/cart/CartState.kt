package com.ajeeb.kart.main.presentation.ui.cart

import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data class CartState(
    val isLoading: Boolean = false
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf()
    }
}