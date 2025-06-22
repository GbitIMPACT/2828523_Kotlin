package com.example.project_2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_2.model.User
import com.example.project_2.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor( // Inyecta decoradores de acuerdo a lo que se use
    private val repository: UserRepository
): ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList()) // Flow parte de asyncronismo
    val users: StateFlow<List<User>> = _users

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchUsers()
    }
    fun fetchUsers() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _users.value = repository.getAllUsers()
            } catch (e: Exception) {
                _error.value = "Error al cargar usuarios: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            try {
                val newUser = User(name = name, email = email)
                repository.createUser(newUser)
                fetchUsers() // Refresaca la lista
            } catch (e: Exception) {
                _error.value = "Error al agregar usuarios: ${e.localizedMessage}"
            }
        }
    }

    fun updateUser(id: Int, name: String, email: String) {
        viewModelScope.launch {
            try {
                val updateUser = User(name = name, email = email)
                repository.updateUser(id, updateUser)
                fetchUsers()
            } catch (e: Exception) {
                _error.value = "Error al actualizar usuario: ${e.localizedMessage}"
            }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteUser(id)
                fetchUsers() // Refresca la lista
            } catch (e: Exception) {
                _error.value = "Error al eliminar usuario: ${e.localizedMessage}"
            }
        }
    }
    private fun startPolling() { // Actuliza los cambios constantemente para cuando hayan cambios en la api
        viewModelScope.launch {
            while (true) {
                fetchUsers()
                delay(3000L)
            }
        }
    }
}