package com.ajeeb.kart.main.presentation.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ajeeb.kart.common.domain.utils.Result
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ajeeb.kart.common.presentation.utils.postSideEffect
import com.ajeeb.kart.common.presentation.utils.reduceState
import com.ajeeb.kart.main.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {

    private val initialState = savedStateHandle.toRoute<HomeState>(HomeState.typeMap)
    override val container =
        viewModelScope.container<HomeState, HomeSideEffect>(initialState)


    fun onEvent(event: HomeIntent) {
       //
    }

    init {
        viewModelScope.launch {
            reduceState { copy(isLoading = true) }
            when (val productsCall = getProductsUseCase()) {
                is Result.Success -> {
                    reduceState { copy(products = productsCall.data, isLoading = false) }
                }

                is Result.Error -> postSideEffect {
                    HomeSideEffect.ShowToast("Unable to fetch products list")
                }
            }
        }
    }

}