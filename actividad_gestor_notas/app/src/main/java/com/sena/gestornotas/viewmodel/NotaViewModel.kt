package com.sena.gestornotas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.gestornotas.data.local.Nota
import com.sena.gestornotas.data.local.repository.NotaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotaViewModel(
    private val repository: NotaRepository,
    private val clienteId: Int
) : ViewModel() {

    val notas: Flow<List<Nota>> = repository.getNotasByClienteId(clienteId)

    fun saveNota(contenido: String) {
        viewModelScope.launch {
            val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(Date())
            val nota = Nota(
                contenido = contenido,
                fecha = fecha,
                clienteId = clienteId
            )
            repository.insertNota(nota)
        }
    }

    fun deleteNota(nota: Nota) {
        viewModelScope.launch {
            repository.deleteNota(nota)
        }
    }

    class Factory(
        private val repository: NotaRepository,
        private val clienteId: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NotaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NotaViewModel(repository, clienteId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}