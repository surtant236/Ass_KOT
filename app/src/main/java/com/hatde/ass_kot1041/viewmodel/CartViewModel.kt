package com.hatde.ass_kot1041.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatde.ass_kot1041.model.CartItem
import com.hatde.ass_kot1041.model.Product
import com.hatde.ass_kot1041.services.CartService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartViewModel : ViewModel() {
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> get() = _cart

    private val api: CartService by lazy {
        Retrofit.Builder()
            .baseUrl("https://689aef87e727e9657f631c55.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CartService::class.java)
    }

    fun fetchCart() {
        viewModelScope.launch {
            try {
                _cart.value = api.getCart()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                val currentList = _cart.value
                val existing = currentList.find { it.productId == product.id }
                if (existing != null && existing.id != null) {
                    // Nếu đã có, tăng số lượng
                    val updated = existing.copy(quantity = existing.quantity + 1)
                    api.updateCartItem(existing.id!!, updated)
                } else {
                    // Nếu chưa có, thêm mới
                    val cartItem = CartItem(
                        productId = product.id,
                        name = product.name,
                        image = product.image,
                        price = product.price,
                        quantity = 1
                    )
                    api.addToCart(cartItem)
                }
                fetchCart()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun increaseQuantity(item: CartItem) {
        viewModelScope.launch {
            try {
                if (item.id != null) {
                    val updated = item.copy(quantity = item.quantity + 1)
                    api.updateCartItem(item.id, updated)
                    fetchCart()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun decreaseQuantity(item: CartItem) {
        viewModelScope.launch {
            try {
                if (item.id != null && item.quantity > 1) {
                    val updated = item.copy(quantity = item.quantity - 1)
                    api.updateCartItem(item.id, updated)
                } else if (item.id != null && item.quantity == 1) {
                    api.deleteCartItem(item.id)
                }
                fetchCart()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeFromCart(item: CartItem) {
        viewModelScope.launch {
            try {
                if (item.id != null) {
                    api.deleteCartItem(item.id)
                    fetchCart()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}