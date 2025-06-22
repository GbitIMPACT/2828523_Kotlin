package com.example.project_2.remote

import com.example.project_2.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Paso 2 peticiones a la api
interface UserApi {
    @GET("user")
    suspend fun getAllUsers(): List<User>

    @POST("user")
    suspend fun createUser(@Body user: User)

    @PUT("user/{id}")
    suspend fun updateUser(@Path ("id") id: Int, @Body user: User)

    @DELETE("user/{id}")
    suspend fun deleteUser(@Path ("id") id: Int)
}