package com.example.aviacionapp.presentation.professor.students

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aviacionapp.di.NetworkModule
import com.example.aviacionapp.utils.NetworkUtils
import com.example.aviacionapp.utils.PreferencesManager
import com.example.aviacionapp.utils.Result
import kotlinx.coroutines.launch

data class StudentItem(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val numeroIdentificacion: String,
    val correoElectronico: String
)

class StudentsViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = NetworkModule.apiService
    private val preferencesManager = PreferencesManager(application)

    private val _studentsState = MutableLiveData<Result<List<StudentItem>>>()
    val studentsState: LiveData<Result<List<StudentItem>>> = _studentsState

    fun loadStudents() {
        viewModelScope.launch {
            _studentsState.value = Result.Loading

            if (!NetworkUtils.isNetworkAvailable(getApplication())) {
                _studentsState.value = Result.Error(Exception("No hay conexión a internet"))
                return@launch
            }

            try {
                val token = preferencesManager.getToken() ?: ""
                val response = apiService.getAllStudents(token)

                if (response.isSuccessful) {
                    response.body()?.let { students ->
                        val studentItems = students.map { student ->
                            StudentItem(
                                id = student.id,
                                nombre = student.nombre,
                                apellido = student.apellido,
                                edad = student.edad,
                                numeroIdentificacion = student.numeroIdentificacion,
                                correoElectronico = student.correoElectronico
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
}