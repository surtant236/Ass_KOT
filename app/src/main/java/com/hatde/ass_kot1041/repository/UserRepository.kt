package com.hatde.ass_kot1041.repository

import com.hatde.ass_kot1041.ApiClient
import com.hatde.ass_kot1041.model.User
import com.hatde.ass_kot1041.services.UserServices
import retrofit2.Response

class UserRepository {
    private val service = ApiClient.retrofit.create(UserServices::class.java)

    suspend fun fetchUsers(): Response<List<User>> {
        return service.getUsers()
    }

    suspend fun registerUser(user: User): Response<User> {
        return service.createUser(user)
    }

    suspend fun updateUser(id: String, user: User): Response<User> {
        return service.updateUser(id, user)
    }

    suspend fun deleteUser(id: String): Response<Unit> {
        return service.deleteUser(id)
    }

    /**
     * "Login" bằng cách lấy danh sách users và so khớp email + password.
     * Lưu ý: so sánh password plaintext chỉ phù hợp cho mock / demo.
     */
    suspend fun login(email: String, password: String): User? {
        val resp = service.getUsers()
        if (resp.isSuccessful) {
            val list = resp.body() ?: emptyList()
            return list.firstOrNull { it.email == email && it.password == password }
        } else {
            return null
        }
    }
}
