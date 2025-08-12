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

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> get() = _totalPrice

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItemIndex = currentItems.indexOfFirst { it.product.id == product.id }
        
        if (existingItemIndex != -1) {
            // If product already exists, increase quantity
            val existingItem = currentItems[existingItemIndex]
            currentItems[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            // Add new product to cart
            currentItems.add(CartItem(product))
        }
        
        _cartItems.value = currentItems
        updateTotalPrice()
    }

    fun removeFromCart(productId: String) {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.removeAll { it.product.id == productId }
        _cartItems.value = currentItems
        updateTotalPrice()
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
            updateTotalPrice()
        }
    }

    private fun updateTotalPrice() {
        val total = _cartItems.value.sumOf { it.product.price * it.quantity }
        _totalPrice.value = total
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        _totalPrice.value = 0
    }
}