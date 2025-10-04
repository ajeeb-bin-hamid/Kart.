package com.ajeeb.kart.main.presentation.ui.cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.kart.common.domain.utils.Result
import com.ajeeb.kart.common.presentation.utils.postSideEffect
import com.ajeeb.kart.common.presentation.utils.reduceState
import com.ajeeb.kart.main.domain.model.CartItem
import com.ajeeb.kart.main.domain.model.Product
import com.ajeeb.kart.main.domain.usecase.AddItemToCartUseCase
import com.ajeeb.kart.main.domain.usecase.GetAllCartItemsUseCase
import com.ajeeb.kart.main.domain.usecase.RemoveItemToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val addItemToCartUseCase: AddItemToCartUseCase,
    private val removeItemToCartUseCase: RemoveItemToCartUseCase
) : ViewModel(), ContainerHost<CartState, CartSideEffect> {

    private val initialState = savedStateHandle.toRoute<CartState>(CartState.typeMap)
    override val container = viewModelScope.container<CartState, CartSideEffect>(initialState)


    fun onEvent(event: CartIntent) {
        when (event) {
            is CartIntent.AddItemToCart -> addItemToCart(event.cartItem)
            is CartIntent.RemoveItemFromCart -> removeItemFromCart(event.cartItem)
        }
    }

    init {
        viewModelScope.launch {
            val cartItems = getAllCartItemsUseCase()
            cartItems.collect {
                reduceState {
                    copy(cartItems = it)
                }
            }
        }
    }

    private fun addItemToCart(cartItem: CartItem) {
        viewModelScope.launch {
            val product = Product(
                itemID = cartItem.itemID,
                itemName = cartItem.itemName,
                sellingPrice = cartItem.sellingPrice,
                taxPercentage = cartItem.taxPercentage
            )
            when (addItemToCartUseCase(product)) {
                is Result.Success -> {
                    //
                }

                is Result.Error -> {
                    postSideEffect { CartSideEffect.ShowToast("Failed to add item to cart") }
                }
            }
        }
    }

    private fun removeItemFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            val product = Product(
                itemID = cartItem.itemID,
                itemName = cartItem.itemName,
                sellingPrice = cartItem.sellingPrice,
                taxPercentage = cartItem.taxPercentage
            )
            when (removeItemToCartUseCase(product)) {
                is Result.Success -> {
                    //
                }

                is Result.Error -> {
                    postSideEffect { CartSideEffect.ShowToast("Failed to add item to cart") }
                }
            }
        }
    }
}