package com.hatde.ass_kot1041.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatde.ass_kot1041.model.Product
import com.hatde.ass_kot1041.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManagementViewModel : ViewModel() {
    private val repository = ProductRepository()
    
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage
    
    init {
        loadProducts()
    }
    
    fun loadProducts() {
        _isLoading.value = true
        _errorMessage.value = null
        
        repository.getAllProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _products.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Không thể tải danh sách sản phẩm"
                }
            }
            
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Lỗi kết nối: ${t.message}"
            }
        })
    }
    
    fun addProduct(product: Product) {
        _isLoading.value = true
        _errorMessage.value = null
        
        repository.addProduct(product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    loadProducts() // Refresh the list
                } else {
                    _errorMessage.value = "Không thể thêm sản phẩm"
                }
            }
            
            override fun onFailure(call: Call<Product>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Lỗi kết nối: ${t.message}"
            }
        })
    }
    
    fun updateProduct(id: String, product: Product) {
        _isLoading.value = true
        _errorMessage.value = null
        
        repository.updateProduct(id, product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    loadProducts() // Refresh the list
                } else {
                    _errorMessage.value = "Không thể cập nhật sản phẩm"
                }
            }
            
            override fun onFailure(call: Call<Product>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Lỗi kết nối: ${t.message}"
            }
        })
    }
    
    fun deleteProduct(id: String) {
        _isLoading.value = true
        _errorMessage.value = null
        
        repository.deleteProduct(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    loadProducts() // Refresh the list
                } else {
                    _errorMessage.value = "Không thể xóa sản phẩm"
                }
            }
            
            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Lỗi kết nối: ${t.message}"
            }
        })
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}