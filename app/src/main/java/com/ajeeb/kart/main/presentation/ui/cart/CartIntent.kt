package com.ajeeb.kart.main.presentation.ui.cart

import com.ajeeb.kart.main.domain.model.CartItem

sealed class CartIntent {
    data class AddItemToCart(val cartItem: CartItem) : CartIntent()
    data class RemoveItemFromCart(val cartItem: CartItem) : CartIntent()
}