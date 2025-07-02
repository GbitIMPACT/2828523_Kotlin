package com.example.aviacionapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GradeDto(
    val id: Int,
    val descripcion: String,
    val valor: Double,
    @SerializedName("fecha_registro")
    val fechaRegistro: String,
    @SerializedName("matricula_id")
    val matriculaId: Int? = null
)

data class AddGradeRequest(
    @SerializedName("matricula_id")
    val matriculaId: Int,
    val descripcion: String,
    val valor: Double
)

data class UpdateGradeRequest(
    val descripcion: String,
    val valor: Double
)