package com.ajeeb.kart.main.presentation.ui.cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<CartState, CartSideEffect> {

    private val initialState = savedStateHandle.toRoute<CartState>(CartState.typeMap)
    override val container =
        viewModelScope.container<CartState, CartSideEffect>(initialState)


    fun onEvent(event: CartIntent) {
        //
    }
}