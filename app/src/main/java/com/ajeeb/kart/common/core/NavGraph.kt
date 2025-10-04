package com.ajeeb.kart.common.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajeeb.kart.main.presentation.ui.cart.CartScreen
import com.ajeeb.kart.main.presentation.ui.cart.CartState
import com.ajeeb.kart.main.presentation.ui.cart.CartViewModel
import com.ajeeb.kart.main.presentation.ui.home.HomeScreen
import com.ajeeb.kart.main.presentation.ui.home.HomeState
import com.ajeeb.kart.main.presentation.ui.home.HomeViewModel


/**
 * NavGraph: This composable function defines the navigation graph for the application.
 *
 * It uses a [NavHost] to manage the navigation between different screens.
 * The navigation is based on the `State` classes ([MainState], [ExpenseState]) which represent
 * the different screens in the application.
 *
 * Each screen is associated with a corresponding ViewModel (e.g., [MainViewModel], [ExpenseViewModel])
 * which is retrieved using Hilt's `hiltViewModel` function.
 *
 * The `state`, `sideEffect`, and `onEvent` from the ViewModels are passed to the respective screen composables.
 *
 * Screens defined:
 *   - [MainState]: Represents the main screen of the application.
 *     - ViewModel: [MainViewModel]
 *     - Composable: [MainScreen]
 *   - [ExpenseState]: Represents the expense screen of the application.
 *     - ViewModel: [ExpenseViewModel]
 *     - Composable: [ExpenseScreen]
 *
 *  Navigation:
 *   - The [startDestination] is set to [MainState], meaning the main screen is the initial screen displayed.
 *   - To navigate between screens, you would call `navController.navigate(NewScreenState())` from within a screen composable.
 *     - Note: No navigation actions are present within this function. This Function only sets up the possible Navigations.
 */
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeState()) {
        composable<HomeState>(HomeState.typeMap) {

            val vm = hiltViewModel<HomeViewModel>()
            val state = vm.container.stateFlow.collectAsState()
            val sideEffect = vm.container.sideEffectFlow
            val onEvent = vm::onEvent

            HomeScreen(state, sideEffect, onEvent) {
                navController.navigate(CartState())
            }
        }

        composable<CartState>(CartState.typeMap) {

            val vm = hiltViewModel<CartViewModel>()
            val state = vm.container.stateFlow.collectAsState()
            val sideEffect = vm.container.sideEffectFlow
            val onEvent = vm::onEvent

            CartScreen(state = state, sideEffect = sideEffect, onEvent = onEvent) {
                navController.popBackStack()
            }
        }
    }
}
