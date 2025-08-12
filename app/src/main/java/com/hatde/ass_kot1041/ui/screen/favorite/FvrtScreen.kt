package com.hatde.ass_kot1041.ui.screen.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        // Header with modern styling
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Yêu thích",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Check if favorites list is empty (placeholder data)
        val favoriteItems = listOf(
            FavoriteItem("Minimal Stand", "$25.00", ""),
            FavoriteItem("Coffee Table", "$45.00", ""),
            FavoriteItem("Modern Chair", "$35.00", ""),
            FavoriteItem("Desk Lamp", "$20.00", "")
        )

        if (favoriteItems.isEmpty()) {
            // Empty favorites state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Chưa có sản phẩm yêu thích",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Thêm sản phẩm vào danh sách yêu thích",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // Favorites list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(favoriteItems) { item ->
                    FavoriteItemCard(
                        item = item,
                        onRemove = { /* Handle remove */ }
                    )
                }
            }
        }
    }
}

data class FavoriteItem(
    val name: String,
    val price: String,
    val image: String
)

@Composable
fun FavoriteItemCard(
    item: FavoriteItem,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product image placeholder
            Card(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Product details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.price,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            // Remove button
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove from favorites",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
