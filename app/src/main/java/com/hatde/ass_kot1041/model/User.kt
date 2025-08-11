package com.hatde.ass_kot1041.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: String? = null,
    val username: String,
    val password: String,
    val email: String
)
