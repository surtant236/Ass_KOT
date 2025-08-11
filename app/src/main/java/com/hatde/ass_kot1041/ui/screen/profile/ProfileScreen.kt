package com.hatde.ass_kot1041.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hatde.ass_kot1041.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    userViewModel: UserViewModel = viewModel()
) {
    val currentUser by remember { mutableStateOf(userViewModel.currentUser.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Profile Picture
        Box(modifier = Modifier.size(120.dp)) {
            if (currentUser?.avatar?.isNotEmpty() == true) {
                AsyncImage(
                    model = currentUser.avatar,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        // User Info
        Text(
            text = currentUser?.name ?: "Khách",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = currentUser?.email ?: "Chưa đăng nhập",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider()

        if (currentUser != null) {
            Text(
                "Thông tin tài khoản",
                modifier = Modifier.padding(top = 16.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "ID: ${currentUser.id ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                "Email: ${currentUser.email ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { userViewModel.logout() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Đăng xuất", color = Color.White)
            }
        } else {
            Text(
                "Bạn chưa đăng nhập",
                modifier = Modifier.padding(top = 16.dp),
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "Vui lòng đăng nhập để xem thông tin tài khoản",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}