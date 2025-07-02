package com.example.aviacionapp.presentation.student.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aviacionapp.data.local.database.AviacionDatabase
import com.example.aviacionapp.di.NetworkModule
import com.example.aviacionapp.utils.NetworkUtils
import com.example.aviacionapp.utils.PreferencesManager
import com.example.aviacionapp.utils.Result
import kotlinx.coroutines.launch

data class StudentProfile(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val tipoIdentificacion: String,
    val numeroIdentificacion: String,
    val correoElectronico: String,
    val fechaRegistro: String,
    val coursesCount: Int,
    val averageGrade: Double
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = NetworkModule.apiService
    private val database = AviacionDatabase.getDatabase(application)
    private val studentDao = database.studentDao()
    private val courseDao = database.courseDao()
    private val preferencesManager = PreferencesManager(application)

    private val _profileState = MutableLiveData<Result<StudentProfile>>()
    val profileState: LiveData<Result<StudentProfile>> = _profileState

    fun loadProfile() {
        viewModelScope.launch {
            _profileState.value = Result.Loading

            try {
                if (NetworkUtils.isNetworkAvailable(getApplication())) {
                    val token = preferencesManager.getToken() ?: ""

                    // Obtener perfil
                    val profileResponse = apiService.getStudentProfile(token)

                    if (profileResponse.isSuccessful) {
                        profileResponse.body()?.let { profile ->
                            // Actualizar datos locales
                            val currentStudent = studentDao.getCurrentStudentSync()
                            currentStudent?.let { student ->
                                val updatedStudent = student.copy(
                                    edad = profile.edad,
                                    tipoIdentificacion = profile.tipoIdentificacion,
                                    numeroIdentificacion = profile.numeroIdentificacion
                                )
                                studentDao.update(updatedStudent)
                            }

                            // Obtener cursos y calcular estadísticas
                            val coursesResponse = apiService.getStudentCourses(token)
                            var coursesCount = 0
                            var totalGrades = 0.0
                            var gradesCount = 0

                            if (coursesResponse.isSuccessful) {
                                coursesResponse.body()?.let { courses ->
                                    coursesCount = courses.size

                                    courses.forEach { course ->
                                        course.notas?.forEach { grade ->
                                            totalGrades += grade.valor
                                            gradesCount++
                                        }
                                    }
                                }
                            }

                            val averageGrade = if (gradesCount > 0) totalGrades / gradesCount else 0.0

                            val studentProfile = StudentProfile(
                                nombre = profile.nombre,
                                apellido = profile.apellido,
                                edad = profile.edad,
                                tipoIdentificacion = profile.tipoIdentificacion,
                                numeroIdentificacion = profile.numeroIdentificacion,
                                correoElectronico = profile.correoElectronico,
                                fechaRegistro = profile.fechaRegistro,
                                coursesCount = coursesCount,
                                averageGrade = averageGrade
                            )

                            _profileState.value = Result.Success(studentProfile)
                        }
                    } else {
                        loadFromLocal()
                    }
                } else {
                    loadFromLocal()
                }
            } catch (e: Exception) {
                loadFromLocal()
            }
        }
    }

    private suspend fun loadFromLocal() {
        val currentStudent = studentDao.getCurrentStudentSync()
        currentStudent?.let { student ->
            // Calcular estadísticas desde la base de datos local
            var coursesCount = 0
            var totalGrades = 0.0
            var gradesCount = 0

            courseDao.getStudentEnrollments(student.id).collect { enrollments ->
                coursesCount = enrollments.size

                enrollments.forEach { enrollment ->
                    courseDao.getGradesByMatricula(enrollment.matriculaId).collect { grades ->
                        grades.forEach { grade ->
                            totalGrades += grade.valor
                            gradesCount++
                        }
                    }
                }
            }

            val averageGrade = if (gradesCount > 0) totalGrades / gradesCount else 0.0

            val studentProfile = StudentProfile(
                nombre = student.nombre,
                apellido = student.apellido,
                edad = student.edad,
                tipoIdentificacion = student.tipoIdentificacion,
                numeroIdentificacion = student.numeroIdentificacion,
                correoElectronico = student.correoElectronico,
                fechaRegistro = "", // No disponible en local
                coursesCount = coursesCount,
                averageGrade = averageGrade
            )

            _profileState.value = Result.Success(studentProfile)
        }
    }
}