package com.ajeeb.kart.main.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import com.ajeeb.kart.main.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    state: State<HomeState>,
    sideEffect: Flow<HomeSideEffect>,
    onEvent: (HomeIntent) -> Unit,
    onClickCart: () -> Unit,
) {

    val context = LocalContext.current

    /**Collect SideEffects using Orbit*/
    LaunchedEffect(Unit) {
        sideEffect.collect { action ->
            when (action) {
                is HomeSideEffect.ShowToast -> {
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
            if (state.value.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF007AFF),
                        strokeWidth = 3.dp
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Kart",
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold)
                    )
                    IconButton(onClick = { onClickCart() }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_shopping_cart),
                            contentDescription = "Cart",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    itemsIndexed(items = state.value.products) { _, product ->
                        ItemCard(product) {
                            onEvent(HomeIntent.AddItemToCart(product))
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun ItemCard(item: Product, onClickAdd: () -> Unit) {
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
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_cart_placeholder),
                contentDescription = item.itemName,
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.itemName,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = "$${item.sellingPrice}",
                    style = MaterialTheme.typography.body1.copy(color = Color.Gray)
                )
            }

            Button(
                onClick = { onClickAdd() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF007AFF)
                )
            ) {
                Text("Add", color = Color.White)
            }
        }
    }
}