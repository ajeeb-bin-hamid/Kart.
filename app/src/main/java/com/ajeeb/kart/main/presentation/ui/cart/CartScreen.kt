package com.ajeeb.kart.main.presentation.ui.cart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ajeeb.kart.R
import com.ajeeb.kart.main.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

@Composable
fun CartScreen(
    state: State<CartState>,
    sideEffect: Flow<CartSideEffect>,
    onEvent: (CartIntent) -> Unit,
    onClickBack: () -> Unit,
) {

    val context = LocalContext.current

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is CartSideEffect.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .safeDrawingPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            // Header
            TopAppBar(
                title = { Text("Cart", style = MaterialTheme.typography.h6) },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )

            // Cart items list
            LazyColumn(
                modifier = Modifier.weight(1f), contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                itemsIndexed(items = state.value.cartItems) { _, item ->
                    CartItemRow(
                        cartItem = item,
                        onAdd = { onEvent(CartIntent.AddItemToCart(item)) },
                        onRemove = { onEvent(CartIntent.RemoveItemFromCart(item)) })
                }
            }

            // Summary
            CartSummary(state.value.cartItems)
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem, onAdd: () -> Unit, onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Product Icon Placeholder
                Image(
                    painter = painterResource(id = R.drawable.ic_cart_placeholder),
                    contentDescription = cartItem.itemName,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(cartItem.itemName, style = MaterialTheme.typography.body1)
                    Text("$${cartItem.sellingPrice}", style = MaterialTheme.typography.body2)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onRemove) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "Remove",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(cartItem.quantity.toString(), style = MaterialTheme.typography.body1)

                IconButton(onClick = onAdd) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Cart",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CartSummary(cartItems: List<CartItem>) {
    val subtotal = cartItems.sumOf { it.sellingPrice * it.quantity }
    val tax = cartItems.sumOf { it.sellingPrice * it.quantity * it.taxPercentage / 100 }
    val total = subtotal + tax

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF9F9F9))
            .padding(16.dp)
    ) {
        SummaryRow("Subtotal", subtotal)
        SummaryRow("Tax", tax)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        SummaryRow("Total", total, isBold = true)
    }
}

@Composable
fun SummaryRow(label: String, amount: Double, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.body1)
        Text(
            "$${"%.2f".format(amount)}",
            style = if (isBold) MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
            else MaterialTheme.typography.body2
        )
    }
}

