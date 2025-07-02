package com.example.aviacionapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CourseDto(

    @SerializedName(value = "id", alternate = ["curso_id"])

    val id: Int,

    @SerializedName(value = "nombre", alternate = ["curso_nombre"])

    val nombre: String?,

    val descripcion: String?,

    @SerializedName("duracion_horas")

    val duracionHoras: Int?,

    val estado: String? = null,

    @SerializedName("matricula_id")

    val matriculaId: Int? = null,

    @SerializedName("fecha_matricula")

    val fechaMatricula: String? = null,

    @SerializedName("estado_matricula")

    val estadoMatricula: String? = null,

    @SerializedName("promedio_notas")

    val promedioNotas: Double? = null,

    val notas: List<GradeDto>? = null

)

data class EnrollRequest(
    @SerializedName("curso_id")
    val cursoId: Int
)

data class EnrollResponse(
    val message: String,
    @SerializedName("matricula_id")
    val matriculaId: Int
)