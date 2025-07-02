package com.example.aviacionapp.presentation.auth.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aviacionapp.data.local.database.AviacionDatabase
import com.example.aviacionapp.data.local.entities.StudentEntity
import com.example.aviacionapp.data.remote.dto.RegisterRequest
import com.example.aviacionapp.di.NetworkModule
import com.example.aviacionapp.utils.NetworkUtils
import com.example.aviacionapp.utils.PreferencesManager
import com.example.aviacionapp.utils.Result
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = NetworkModule.apiService
    private val database = AviacionDatabase.getDatabase(application)
    private val studentDao = database.studentDao()
    private val preferencesManager = PreferencesManager(application)

    private val _registerResult = MutableLiveData<Result<Boolean>>()
    val registerResult: LiveData<Result<Boolean>> = _registerResult

    fun register(
        nombre: String,
        apellido: String,
        edad: Int,
        tipoIdentificacion: String,
        numeroIdentificacion: String,
        correoElectronico: String,
        contrasena: String
    ) {
        viewModelScope.launch {
            _registerResult.value = Result.Loading

            if (!NetworkUtils.isNetworkAvailable(getApplication())) {
                // Guardar en local para sincronizar después
                val studentEntity = StudentEntity(
                    id = System.currentTimeMillis().toInt(),
                    nombre = nombre,
                    apellido = apellido,
                    edad = edad,
                    tipoIdentificacion = tipoIdentificacion,
                    numeroIdentificacion = numeroIdentificacion,
                    correoElectronico = correoElectronico,
                    token = null,
                    isLoggedIn = false,
                    isProfessor = false
                )
                studentDao.insert(studentEntity)
                _registerResult.value = Result.Error(Exception("Registro guardado localmente. Se sincronizará cuando haya conexión."))
                return@launch
            }

            try {
                val request = RegisterRequest(
                    nombre = nombre,
                    apellido = apellido,
                    edad = edad,
                    tipoIdentificacion = tipoIdentificacion,
                    numeroIdentificacion = numeroIdentificacion,
                    correoElectronico = correoElectronico,
                    contrasena = contrasena
                )

                val response = apiService.register(request)

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
                                edad = edad,
                                tipoIdentificacion = tipoIdentificacion,
                                numeroIdentificacion = numeroIdentificacion,
                                correoElectronico = student.correoElectronico,
                                token = authResponse.token,
                                isLoggedIn = true,
                                isProfessor = false
                            )
                            studentDao.logoutAll()
                            studentDao.insert(studentEntity)
                        }

                        _registerResult.value = Result.Success(true)
                    }
                } else {
                    val errorMessage = when (response.code()) {
                        400 -> "El correo o identificación ya está registrado"
                        else -> "Error al registrar"
                    }
                    _registerResult.value = Result.Error(Exception(errorMessage))
                }
            } catch (e: Exception) {
                _registerResult.value = Result.Error(e)
            }
        }
    }
}