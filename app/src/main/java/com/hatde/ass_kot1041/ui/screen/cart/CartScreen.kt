package com.hatde.ass_kot1041.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.hatde.ass_kot1041.viewmodel.CartViewModel
import com.hatde.ass_kot1041.model.CartItem

@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel()) {
    val cart by cartViewModel.cart.collectAsState()

    // Luôn fetch cart khi vào màn giỏ hàng
    LaunchedEffect(Unit) {
        cartViewModel.fetchCart()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Giỏ hàng", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cart) { item ->
                CartItemRow(
                    item = item,
                    onIncrease = { cartViewModel.increaseQuantity(item) },
                    onDecrease = { cartViewModel.decreaseQuantity(item) },
                    onRemove = { cartViewModel.removeFromCart(item) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        val total = cart.sumOf { it.price * it.quantity }
        Text("Total: ${total} VND", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { /* handle checkout */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Check out")
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color(0xFFF0F0F0)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.foundation.Image(
            painter = rememberAsyncImagePainter(item.image),
            contentDescription = item.name,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontWeight = FontWeight.Bold)
            Text("${item.price} VND", color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecrease) {
                Icon(Icons.Default.Remove, contentDescription = "Giảm")
            }
            Text(item.quantity.toString(), modifier = Modifier.padding(horizontal = 4.dp))
            IconButton(onClick = onIncrease) {
                Icon(Icons.Default.Add, contentDescription = "Tăng")
            }
        }
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Xóa")
        }
    }
}