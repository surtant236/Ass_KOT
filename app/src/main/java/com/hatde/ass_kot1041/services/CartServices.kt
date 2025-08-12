package com.hatde.ass_kot1041.services

import com.hatde.ass_kot1041.model.CartItem
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface CartService {
    @GET("carts")
    suspend fun getCart(): List<CartItem>

    @POST("carts")
    suspend fun addToCart(@Body item: CartItem): CartItem

    @PUT("carts/{id}")
    suspend fun updateCartItem(@Path("id") id: String, @Body item: CartItem): CartItem

    @DELETE("carts/{id}")
    suspend fun deleteCartItem(@Path("id") id: String)
}