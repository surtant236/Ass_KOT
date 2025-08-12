package com.hatde.ass_kot1041.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hatde.ass_kot1041.viewmodel.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        cartViewModel.fetchCart()
    }
    val cart by cartViewModel.cart.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text("My cart", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cart) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color(0xFFF0F0F0)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hiển thị ảnh sản phẩm
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
                    Row {
                        // Thêm nút tăng giảm nếu muốn
                        Text(item.quantity.toString(), modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
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