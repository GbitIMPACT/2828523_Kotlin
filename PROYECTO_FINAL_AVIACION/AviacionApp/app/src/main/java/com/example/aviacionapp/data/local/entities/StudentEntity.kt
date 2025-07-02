package com.example.aviacionapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val tipoIdentificacion: String,
    val numeroIdentificacion: String,
    val correoElectronico: String,
    val token: String? = null,
    val isLoggedIn: Boolean = false,
    val isProfessor: Boolean = false
)

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val duracionHoras: Int,
    val estado: String
)

@Entity(tableName = "enrollments")
data class EnrollmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val studentId: Int,
    val courseId: Int,
    val matriculaId: Int,
    val fechaMatricula: String,
    val estado: String,
    val promedioNotas: Double = 0.0,
    val syncStatus: SyncStatus = SyncStatus.SYNCED
)

@Entity(tableName = "grades")
data class GradeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val matriculaId: Int,
    val descripcion: String,
    val valor: Double,
    val fechaRegistro: String,
    val syncStatus: SyncStatus = SyncStatus.SYNCED
)

enum class SyncStatus {
    SYNCED,
    PENDING_SYNC,
    PENDING_DELETE
}