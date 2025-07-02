package com.example.aviacionapp.data.remote.dto

import com.google.gson.annotations.SerializedName

// Request DTOs
data class RegisterRequest(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    @SerializedName("tipo_identificacion")
    val tipoIdentificacion: String,
    @SerializedName("numero_identificacion")
    val numeroIdentificacion: String,
    @SerializedName("correo_electronico")
    val correoElectronico: String,
    val contrasena: String
)

data class LoginRequest(
    @SerializedName("correo_electronico")
    val correoElectronico: String,
    val contrasena: String
)

data class ProfessorLoginRequest(
    @SerializedName("codigo_instructor")
    val codigoInstructor: String,
    val contrasena: String
)

// Response DTOs
data class AuthResponse(
    val message: String,
    val token: String,
    val estudiante: StudentInfo? = null,
    val profesor: ProfessorInfo? = null
)

data class StudentInfo(
    val id: Int,
    val nombre: String,
    val apellido: String,
    @SerializedName("correo_electronico")
    val correoElectronico: String
)

data class ProfessorInfo(
    val id: Int,
    val nombre: String,
    @SerializedName("codigo_instructor")
    val codigoInstructor: String
)