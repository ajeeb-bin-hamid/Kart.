package com.ajeeb.kart.main.presentation.ui.cart

sealed class CartSideEffect {
    // Actions that can be performed on the UI 
    data class ShowToast(val message: String) : CartSideEffect()

    // Actions that can be used to invoke functions on the ViewModel
}