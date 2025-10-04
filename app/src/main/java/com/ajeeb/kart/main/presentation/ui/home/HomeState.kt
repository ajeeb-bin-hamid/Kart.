package com.ajeeb.kart.main.presentation.ui.home

import androidx.navigation.NavType
import com.ajeeb.kart.common.presentation.utils.navType
import com.ajeeb.kart.main.domain.model.Product
import kotlinx.serialization.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Serializable
data class HomeState(
    val isLoading: Boolean = false,
    val products: List<Product> = listOf()
) {
    companion object {
        val typeMap: Map<KType, NavType<out Any>> = mapOf(
            typeOf<List<Product>>() to navType<List<Product>>()
        )
    }
}