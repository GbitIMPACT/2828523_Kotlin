package com.example.project_2.repository

import com.example.project_2.model.User
import com.example.project_2.remote.UserApi
import java.security.PrivilegedAction
import javax.inject.Inject

// Paso 3 el repository, para el manejo de peticiones
class UserRepository @Inject constructor(
    private val api: UserApi
) { // El inject permite traer el user API para la peticiones deacuerdo al remote
    suspend fun getAllUsers(): List<User> {
        return api.getAllUsers()
    }

    suspend fun createUser(user: User) {
        api.createUser(user)
    }

    suspend fun updateUser(id: Int, user: User) {
        api.updateUser(id, user)
    }

    suspend fun deleteUser(id: Int) {
        api.deleteUser(id)
    }
}