package com.hatde.ass_kot1041.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.hatde.ass_kot1041.viewmodel.ProductViewModel

@Composable
fun HomeScreen(navController: NavController, productViewModel: ProductViewModel = viewModel()) {
    val products by productViewModel.products.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Make home",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "BEAUTIFUL",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                tint = Color.Gray,
                modifier = Modifier.clickable { navController.navigate("cart") }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clickable { navController.navigate("product_detail/${product.id}") }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(product.image),
                            contentDescription = product.name,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(product.name, style = MaterialTheme.typography.titleMedium)
                        Text("${product.price} VND", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}