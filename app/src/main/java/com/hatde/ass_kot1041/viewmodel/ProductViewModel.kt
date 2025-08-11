package com.hatde.ass_kot1041.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatde.ass_kot1041.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProductApi {
    @GET("products") // endpoint sau base URL
    suspend fun getProducts(): List<Product>
}

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    private val api: ProductApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://689a1e8bfed141b96ba1ee55.mockapi.io/") // base url
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                _products.value = api.getProducts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
