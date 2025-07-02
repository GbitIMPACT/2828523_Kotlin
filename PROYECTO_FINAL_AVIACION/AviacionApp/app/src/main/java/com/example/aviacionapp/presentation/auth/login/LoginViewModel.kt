package com.example.aviacionapp.presentation.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aviacionapp.data.local.database.AviacionDatabase
import com.example.aviacionapp.data.local.entities.StudentEntity
import com.example.aviacionapp.data.remote.dto.LoginRequest
import com.example.aviacionapp.data.remote.dto.ProfessorLoginRequest
import com.example.aviacionapp.di.NetworkModule
import com.example.aviacionapp.utils.NetworkUtils
import com.example.aviacionapp.utils.PreferencesManager
import com.example.aviacionapp.utils.Result
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = NetworkModule.apiService
    private val database = AviacionDatabase.getDatabase(application)
    private val studentDao = database.studentDao()
    private val preferencesManager = PreferencesManager(application)

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    fun loginStudent(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading

            if (!NetworkUtils.isNetworkAvailable(getApplication())) {
                _loginResult.value = Result.Error(Exception("No hay conexión a internet"))
                return@launch
            }

            try {
                val response = apiService.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    response.body()?.let { authResponse ->
                        // Guardar token y datos del usuario
                        preferencesManager.saveAuthData(
                            token = "Bearer ${authResponse.token}",
                            userType = "student",
                            userId = authResponse.estudiante?.id ?: 0,
                            userName = "${authResponse.estudiante?.nombre} ${authResponse.estudiante?.apellido}"
                        )

                        // Guardar en base de datos local
                        authResponse.estudiante?.let { student ->
                            val studentEntity = StudentEntity(
                                id = student.id,
                                nombre = student.nombre,
                                apellido = student.apellido,
                                edad = 0, // Se actualizará con el perfil completo
                                tipoIdentificacion = "",
                                numeroIdentificacion = "",
                                correoElectronico = student.correoElectronico,
                                token = authResponse.token,
                                isLoggedIn = true,
                                isProfessor = false
                            )
                            studentDao.logoutAll()
                            studentDao.insert(studentEntity)
                        }

                        _loginResult.value = Result.Success("student")
                    }
                } else {
                    _loginResult.value = Result.Error(Exception("Credenciales inválidas"))
                }
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e)
            }
        }
    }

    fun loginProfessor(codigoInstructor: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading

            if (!NetworkUtils.isNetworkAvailable(getApplication())) {
                _loginResult.value = Result.Error(Exception("No hay conexión a internet"))
                return@launch
            }

            try {
                val response = apiService.loginProfessor(
                    ProfessorLoginRequest(codigoInstructor, password)
                )

                if (response.isSuccessful) {
                    response.body()?.let { authResponse ->
                        // Guardar token y datos del profesor
                        preferencesManager.saveAuthData(
                            token = "Bearer ${authResponse.token}",
                            userType = "professor",
                            userId = authResponse.profesor?.id ?: 0,
                            userName = authResponse.profesor?.nombre ?: ""
                        )

                        _loginResult.value = Result.Success("professor")
                    }
                } else {
                    _loginResult.value = Result.Error(Exception("Credenciales inválidas"))
                }
            } catch (e: Exception) {
                _loginResult.value = Result.Error(e)
            }
        }
    }
}