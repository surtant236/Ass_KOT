package com.hatde.ass_kot1041.repository

import com.hatde.ass_kot1041.model.Product
import com.hatde.ass_kot1041.services.ProductServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepository {
    private val service: ProductServices

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://689a1e8bfed141b96ba1ee55.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ProductServices::class.java)
    }

    fun getAllProducts() = service.getAllProducts()
    fun addProduct(product: Product) = service.addProduct(product)
    fun updateProduct(id: String, product: Product) = service.updateProduct(id, product)
    fun deleteProduct(id: String) = service.deleteProduct(id)
}