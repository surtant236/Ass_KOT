package com.hatde.ass_kot1041.repository

import com.hatde.ass_kot1041.ApiClient
import com.hatde.ass_kot1041.model.Product
import com.hatde.ass_kot1041.services.ProductServices

class ProductRepository {
    private val service: ProductServices = ApiClient.retrofit.create(ProductServices::class.java)

    fun getAllProducts() = service.getAllProducts()
    fun addProduct(product: Product) = service.addProduct(product)
    fun updateProduct(id: String, product: Product) = service.updateProduct(id, product)
    fun deleteProduct(id: String) = service.deleteProduct(id)
}