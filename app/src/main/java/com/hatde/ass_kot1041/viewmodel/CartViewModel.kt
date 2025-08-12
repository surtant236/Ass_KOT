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
            .baseUrl("https://689a1e8bfed141b96ba1ee56.mockapi.io/")
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
                val cartItem = CartItem(
                    productId = product.id,
                    name = product.name,
                    image = product.image,
                    price = product.price,
                    quantity = 1
                )
                api.addToCart(cartItem)
                fetchCart() // update lại danh sách sau khi thêm
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}