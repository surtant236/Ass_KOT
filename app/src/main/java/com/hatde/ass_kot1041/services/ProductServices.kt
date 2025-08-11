package com.hatde.ass_kot1041.services

import com.hatde.ass_kot1041.model.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductServices {
    @GET("products")
    fun getAllProducts(): Call<List<Product>>

    @POST("products")
    fun addProduct(@Body product: Product): Call<Product>

    @PUT("products/{id}")
    fun updateProduct(@Path("id") id: String, @Body product: Product): Call<Product>

    @DELETE("products/{id}")
    fun deleteProduct(@Path("id") id: String): Call<Void>
}
