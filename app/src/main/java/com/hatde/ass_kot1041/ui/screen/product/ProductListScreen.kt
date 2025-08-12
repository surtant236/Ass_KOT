package com.hatde.ass_kot1041.ui.screen.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.hatde.ass_kot1041.viewmodel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductListScreen(
    navController: NavController,
    productViewModel: ProductViewModel = viewModel()
) {
    val products by productViewModel.products.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("product_detail/${product.id}")
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(product.image),
                        contentDescription = product.name,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(product.name, style = MaterialTheme.typography.titleMedium)
                        Text("${product.price} VND", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}