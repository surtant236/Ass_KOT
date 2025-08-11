package com.hatde.ass_kot1041.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hatde.ass_kot1041.model.Product
import com.hatde.ass_kot1041.viewmodel.ProductManagementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductManagementScreen(
    viewModel: ProductManagementViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quản lý sản phẩm",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            FloatingActionButton(
                onClick = { showAddDialog = true },
                modifier = Modifier.size(56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm sản phẩm")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Error message
        errorMessage?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Loading indicator
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        
        // Product list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onEdit = {
                        selectedProduct = product
                        showEditDialog = true
                    },
                    onDelete = {
                        selectedProduct = product
                        showDeleteDialog = true
                    }
                )
            }
        }
    }
    
    // Add Product Dialog
    if (showAddDialog) {
        ProductFormDialog(
            title = "Thêm sản phẩm",
            product = null,
            onDismiss = { showAddDialog = false },
            onConfirm = { product ->
                viewModel.addProduct(product)
                showAddDialog = false
            }
        )
    }
    
    // Edit Product Dialog
    if (showEditDialog && selectedProduct != null) {
        ProductFormDialog(
            title = "Sửa sản phẩm",
            product = selectedProduct,
            onDismiss = { showEditDialog = false },
            onConfirm = { product ->
                viewModel.updateProduct(selectedProduct!!.id, product)
                showEditDialog = false
                selectedProduct = null
            }
        )
    }
    
    // Delete Confirmation Dialog
    if (showDeleteDialog && selectedProduct != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa sản phẩm '${selectedProduct!!.name}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteProduct(selectedProduct!!.id)
                        showDeleteDialog = false
                        selectedProduct = null
                    }
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}

@Composable
fun ProductItem(
    product: Product,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${product.price} VNĐ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = product.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Sửa",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Xóa",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            
            if (product.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormDialog(
    title: String,
    product: Product?,
    onDismiss: () -> Unit,
    onConfirm: (Product) -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var category by remember { mutableStateOf(product?.category ?: "") }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var image by remember { mutableStateOf(product?.image ?: "") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên sản phẩm") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Giá (VNĐ)") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Danh mục") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = image,
                    onValueChange = { image = it },
                    label = { Text("URL hình ảnh") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mô tả") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val priceInt = price.toIntOrNull() ?: 0
                    val newProduct = Product(
                        id = product?.id ?: "",
                        name = name,
                        price = priceInt,
                        category = category,
                        image = image,
                        description = description
                    )
                    onConfirm(newProduct)
                },
                enabled = name.isNotBlank() && price.isNotBlank()
            ) {
                Text("Lưu")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}