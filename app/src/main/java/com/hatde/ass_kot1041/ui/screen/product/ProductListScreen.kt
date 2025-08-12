package com.hatde.ass_kot1041.ui.screen.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        // Header with modern styling
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Danh sách sản phẩm",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Products list with enhanced cards
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("product_detail/${product.id}")
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Product image with rounded corners
                        Card(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(product.image),
                                contentDescription = product.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        // Product details
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Price with prominent styling
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "${product.price} VND",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                        
                        // Arrow or action indicator could go here if needed
                        Icon(
                            imageVector = Icons.Default.List, // Replace with appropriate arrow icon
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}