package com.example.project_2.model

import com.squareup.moshi.JsonClass

// Paso 2 los modelos
@JsonClass(
    generateAdapter = true
)
data class User(
    val id: Int = 0, // Por defecto para nuevos usuarios
    val name: String,
    val email: String
)