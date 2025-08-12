package com.hatde.ass_kot1041.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hatde.ass_kot1041.viewmodel.ProductViewModel
import com.hatde.ass_kot1041.viewmodel.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun ProductDetailScreen(
    productId: String,
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val product = productViewModel.products.collectAsState().value.find { it.id == productId }
    var addedToCart by remember { mutableStateOf(false) }

    if (product != null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(product.name, style = MaterialTheme.typography.headlineSmall)
            Text("${product.price} VND", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    cartViewModel.addToCart(product)
                    addedToCart = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thêm vào giỏ hàng")
            }
            if (addedToCart) {
                Text("Đã thêm vào giỏ hàng!", color = Color.Green, modifier = Modifier.padding(top = 8.dp))
            }
        }
    } else {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Không tìm thấy sản phẩm.")
        }
    }
}