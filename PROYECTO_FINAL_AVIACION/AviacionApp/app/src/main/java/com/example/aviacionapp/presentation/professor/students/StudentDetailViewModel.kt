package com.example.aviacionapp.presentation.professor.students

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aviacionapp.data.remote.dto.AddGradeRequest
import com.example.aviacionapp.di.NetworkModule
import com.example.aviacionapp.utils.NetworkUtils
import com.example.aviacionapp.utils.PreferencesManager
import com.example.aviacionapp.utils.Result
import kotlinx.coroutines.launch

data class StudentDetails(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val tipoIdentificacion: String,
    val numeroIdentificacion: String,
    val correoElectronico: String,
    val courses: List<CourseWithGrades>
)

data class CourseWithGrades(
    val courseId: Int,
    val courseName: String,
    val matriculaId: Int,
    val promedio: Double,
    val grades: List<Grade>
)

data class Grade(
    val id: Int,
    val descripcion: String,
    val valor: Double,
    val fecha: String
)

class StudentDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = NetworkModule.apiService
    private val preferencesManager = PreferencesManager(application)

    private val _studentDetails = MutableLiveData<Result<StudentDetails>>()
    val studentDetails: LiveData<Result<StudentDetails>> = _studentDetails

    private val _addGradeResult = MutableLiveData<Result<Boolean>>()
    val addGradeResult: LiveData<Result<Boolean>> = _addGradeResult

    fun loadStudentDetails(studentId: Int) {
        viewModelScope.launch {
            _studentDetails.value = Result.Loading

            if (!NetworkUtils.isNetworkAvailable(getApplication())) {
                _studentDetails.value = Result.Error(Exception("No hay conexión a internet"))
                return@launch
            }

            try {
                val token = preferencesManager.getToken() ?: ""

                // Primero obtener información básica del estudiante
                val studentsResponse = apiService.getAllStudents(token)

                if (studentsResponse.isSuccessful) {
                    val student = studentsResponse.body()?.find { it.id == studentId }

                    if (student != null) {
                        // Obtener todos los cursos
                        val allCoursesResponse = apiService.getCourses(token)

                        val coursesWithGrades = mutableListOf<CourseWithGrades>()

                        if (allCoursesResponse.isSuccessful) {
                            val allCourses = allCoursesResponse.body() ?: emptyList()

                            // Para cada curso, verificar si el estudiante está matriculado
                            allCourses.forEach { course ->
                                try {
                                    val studentsInCourseResponse = apiService.getCourseStudents(token, course.id)

                                    if (studentsInCourseResponse.isSuccessful) {
                                        val studentInCourse = studentsInCourseResponse.body()?.find {
                                            it.estudianteId == studentId
                                        }

                                        if (studentInCourse != null) {
                                            // El estudiante está matriculado en este curso
                                            // Obtener las notas para esta matrícula
                                            coursesWithGrades.add(
                                                CourseWithGrades(
                                                    courseId = course.id,
                                                    courseName = course.nombre ?: "Sin nombre",
                                                    matriculaId = studentInCourse.matriculaId,
                                                    promedio = studentInCourse.promedioNotas,
                                                    grades = emptyList() // Por ahora vacío, podrías cargar las notas aquí
                                                )
                                            )
                                        }
                                    }
                                } catch (e: Exception) {
                                    android.util.Log.e("StudentDetailVM", "Error loading course ${course.id}: ${e.message}")
                                }
                            }
                        }

                        val details = StudentDetails(
                            id = student.id,
                            nombre = student.nombre,
                            apellido = student.apellido,
                            edad = student.edad,
                            tipoIdentificacion = "CC", // Por defecto, ya que no viene en la respuesta
                            numeroIdentificacion = student.numeroIdentificacion,
                            correoElectronico = student.correoElectronico,
                            courses = coursesWithGrades
                        )

                        _studentDetails.value = Result.Success(details)
                    } else {
                        _studentDetails.value = Result.Error(Exception("Estudiante no encontrado"))
                    }
                } else {
                    _studentDetails.value = Result.Error(Exception("Error al cargar datos"))
                }
            } catch (e: Exception) {
                android.util.Log.e("StudentDetailVM", "Exception: ${e.message}", e)
                _studentDetails.value = Result.Error(e)
            }
        }
    }

    fun addGrade(matriculaId: Int, descripcion: String, valor: Double) {
        viewModelScope.launch {
            _addGradeResult.value = Result.Loading

            try {
                val token = preferencesManager.getToken() ?: ""
                val request = AddGradeRequest(matriculaId, descripcion, valor)
                val response = apiService.addGrade(token, request)

                if (response.isSuccessful) {
                    _addGradeResult.value = Result.Success(true)
                } else {
                    _addGradeResult.value = Result.Error(Exception("Error al agregar nota"))
                }
            } catch (e: Exception) {
                _addGradeResult.value = Result.Error(e)
            }
        }
    }
}