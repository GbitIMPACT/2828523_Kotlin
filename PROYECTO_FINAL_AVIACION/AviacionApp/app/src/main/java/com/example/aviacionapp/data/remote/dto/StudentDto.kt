package com.example.aviacionapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StudentProfileDto(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    @SerializedName("tipo_identificacion")
    val tipoIdentificacion: String,
    @SerializedName("numero_identificacion")
    val numeroIdentificacion: String,
    @SerializedName("correo_electronico")
    val correoElectronico: String,
    @SerializedName("fecha_registro")
    val fechaRegistro: String
)

data class StudentDto(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    @SerializedName("numero_identificacion")
    val numeroIdentificacion: String,
    @SerializedName("correo_electronico")
    val correoElectronico: String
)

data class StudentWithGradesDto(
    @SerializedName("estudiante_id")
    val estudianteId: Int,
    val nombre: String,
    val apellido: String,
    @SerializedName("numero_identificacion")
    val numeroIdentificacion: String,
    @SerializedName("correo_electronico")
    val correoElectronico: String,
    @SerializedName("matricula_id")
    val matriculaId: Int,
    @SerializedName("promedio_notas")
    val promedioNotas: Double
)

data class MessageResponse(
    val message: String
)