package com.hatde.ass_kot1041.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatde.ass_kot1041.ApiClient
import com.hatde.ass_kot1041.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.http.GET

interface ProductApi {
    @GET("products") // endpoint sau base URL
    suspend fun getProducts(): List<Product>
}

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val api: ProductApi = ApiClient.retrofit.create(ProductApi::class.java)

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _products.value = api.getProducts()
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Lỗi khi tải sản phẩm"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun retryFetch() {
        fetchProducts()
    }
}
