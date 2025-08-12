package com.hatde.ass_kot1041.model

data class CartItem(
    val id: String? = null,
    val productId: String,
    val name: String,
    val image: String,
    val price: Int,
    val quantity: Int = 1
)