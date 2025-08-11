package com.hatde.ass_kot1041.viewmodel

import androidx.lifecycle.ViewModel
import com.hatde.ass_kot1041.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CartItem(
    val product: Product,
    val quantity: Int = 1
)

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItemIndex = currentItems.indexOfFirst { it.product.id == product.id }
        
        if (existingItemIndex != -1) {
            // Update quantity if product already exists
            val existingItem = currentItems[existingItemIndex]
            currentItems[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            // Add new item
            currentItems.add(CartItem(product = product, quantity = 1))
        }
        
        _cartItems.value = currentItems
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }
        
        val currentItems = _cartItems.value.toMutableList()
        val itemIndex = currentItems.indexOfFirst { it.product.id == productId }
        
        if (itemIndex != -1) {
            currentItems[itemIndex] = currentItems[itemIndex].copy(quantity = quantity)
            _cartItems.value = currentItems
        }
    }

    fun getTotalPrice(): Int {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}