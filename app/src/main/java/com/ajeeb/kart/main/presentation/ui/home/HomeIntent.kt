package com.ajeeb.kart.main.presentation.ui.home

import com.ajeeb.kart.main.domain.model.Product

sealed class HomeIntent {
    data class AddItemToCart(val product: Product) : HomeIntent()
}