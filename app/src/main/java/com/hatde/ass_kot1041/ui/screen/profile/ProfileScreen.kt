package com.hatde.ass_kot1041.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hatde.ass_kot1041.R

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = Modifier.size(120.dp)) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Admin", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("admin@gmail.com", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))
        Divider()

        Text("Thông tin tài khoản", modifier = Modifier.padding(top = 16.dp), fontWeight = FontWeight.Bold)
        Text("Địa chỉ: 123 Đường ABC, TP ABC")
        Text("Số điện thoại: 0123 456 789")

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { /* TODO: Đăng xuất */ }) {
            Text("Đăng xuất")
        }
    }
}