package com.example.aviacionapp.presentation.professor.courses

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

data class CourseStudentItem(
    val estudianteId: Int,
    val nombre: String,
    val apellido: String,
    val numeroIdentificacion: String,
    val correoElectronico: String,
    val matriculaId: Int,
    val promedioNotas: Double
)

class CourseStudentsViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = NetworkModule.apiService
    private val preferencesManager = PreferencesManager(application)

    private val _studentsState = MutableLiveData<Result<List<CourseStudentItem>>>()
    val studentsState: LiveData<Result<List<CourseStudentItem>>> = _studentsState

    private val _addGradeResult = MutableLiveData<Result<Boolean>>()
    val addGradeResult: LiveData<Result<Boolean>> = _addGradeResult

    fun loadCourseStudents(courseId: Int) {
        viewModelScope.launch {
            _studentsState.value = Result.Loading

            if (!NetworkUtils.isNetworkAvailable(getApplication())) {
                _studentsState.value = Result.Error(Exception("No hay conexión a internet"))
                return@launch
            }

            try {
                val token = preferencesManager.getToken() ?: ""
                val response = apiService.getCourseStudents(token, courseId)

                if (response.isSuccessful) {
                    response.body()?.let { students ->
                        val studentItems = students.map { student ->
                            CourseStudentItem(
                                estudianteId = student.estudianteId,
                                nombre = student.nombre,
                                apellido = student.apellido,
                                numeroIdentificacion = student.numeroIdentificacion,
                                correoElectronico = student.correoElectronico,
                                matriculaId = student.matriculaId,
                                promedioNotas = student.promedioNotas
                            )
                        }
                        _studentsState.value = Result.Success(studentItems)
                    }
                } else {
                    _studentsState.value = Result.Error(Exception("Error al cargar estudiantes"))
                }
            } catch (e: Exception) {
                _studentsState.value = Result.Error(e)
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