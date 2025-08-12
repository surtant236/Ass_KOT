package com.hatde.ass_kot1041.services

import com.hatde.ass_kot1041.model.CartItem
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface CartService {
    @GET("carts")
    suspend fun getCart(): List<CartItem>

    @POST("carts")
    suspend fun addToCart(@Body item: CartItem): CartItem
}