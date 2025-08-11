package com.hatde.ass_kot1041.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.hatde.ass_kot1041.model.User
import com.hatde.ass_kot1041.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = UserRepository()

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val currentUser = mutableStateOf<User?>(null)

    private val prefs: SharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    init {
        // load saved user if any
        val json = prefs.getString("logged_user", null)
        if (json != null) {
            currentUser.value = gson.fromJson(json, User::class.java)
        }
    }

    fun saveUserLocally(user: User) {
        currentUser.value = user
        prefs.edit().putString("logged_user", gson.toJson(user)).apply()
    }

    fun logout() {
        currentUser.value = null
        prefs.edit().remove("logged_user").apply()
    }

    fun register(username: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val user = User(username = username, email = email, password = password)
                val resp = repo.registerUser(user)
                if (resp.isSuccessful) {
                    val created = resp.body()
                    if (created != null) {
                        saveUserLocally(created)
                        onResult(true, null)
                    } else {
                        onResult(false, "Không nhận được phản hồi từ server")
                    }
                } else {
                    onResult(false, "Đăng ký thất bại: ${resp.code()} ${resp.message()}")
                }
            } catch (e: Exception) {
                onResult(false, e.localizedMessage ?: "Lỗi mạng")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun login(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val user = repo.login(username, password)
                if (user != null) {
                    saveUserLocally(user)
                    onResult(true, null)
                } else {
                    onResult(false, "Username hoặc mật khẩu không đúng")
                }
            } catch (e: Exception) {
                onResult(false, e.localizedMessage ?: "Lỗi mạng")
            } finally {
                isLoading.value = false
            }
        }
    }
}
