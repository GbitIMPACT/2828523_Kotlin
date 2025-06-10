package com.sena.gestornotas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.gestornotas.data.local.Cliente
import com.sena.gestornotas.data.local.repository.NotaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClienteViewModel(private val repository: NotaRepository) : ViewModel() {
    val clientes = repository.getAllClientes()

    private val _uiState = MutableStateFlow(ClienteUiState())
    val uiState: StateFlow<ClienteUiState> = _uiState

    private val _clienteToEdit = MutableStateFlow<Cliente?>(null)
    val clienteToEdit: StateFlow<Cliente?> = _clienteToEdit

    fun loadCliente(clienteId: Int) {
        viewModelScope.launch {
            _clienteToEdit.value = repository.getClienteById(clienteId)
        }
    }

    fun saveCliente(nombre: String, correo: String) {
        viewModelScope.launch {
            val cliente = Cliente(nombre = nombre, correo = correo)
            repository.insertCliente(cliente)
        }
    }

    fun updateCliente(id: Int, nombre: String, correo: String) {
        viewModelScope.launch {
            val cliente = Cliente(id = id, nombre = nombre, correo = correo)
            repository.updateCliente(cliente)
        }
    }

    fun deleteCliente(cliente: Cliente) {
        viewModelScope.launch {
            repository.deleteCliente(cliente)
        }
    }

    class Factory(private val repository: NotaRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClienteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ClienteViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

data class ClienteUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)