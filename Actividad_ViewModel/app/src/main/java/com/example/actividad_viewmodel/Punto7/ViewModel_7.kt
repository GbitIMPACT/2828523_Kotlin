package com.example.actividad_viewmodel.Punto7

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Tarea(
    val id: Int,
    val titulo: String,
    var completada: Boolean = false
)

class TareasViewModel : ViewModel() {

    private val _tareas = mutableStateListOf<Tarea>()
    val tareas: List<Tarea> = _tareas

    var nuevaTarea by mutableStateOf("")
        private set

    init {

        _tareas.addAll(listOf(
            Tarea(1, "Estudiar Python"),
            Tarea(2, "Estudiar Kotlin"),
            Tarea(3, "Hacer ejercicio"),
            Tarea(4, "Reservar la comida")
        ))
    }

    fun actualizarNuevaTarea(texto: String) {
        nuevaTarea = texto
    }


    fun agregarTarea() {
        if (nuevaTarea.isNotEmpty()) {
            val nuevaId = (_tareas.maxByOrNull { it.id }?.id ?: 0) + 1
            _tareas.add(Tarea(nuevaId, nuevaTarea))
            nuevaTarea = "" // Limpiar campo
        }
    }


    fun cambiarEstadoTarea(id: Int) {
        val index = _tareas.indexOfFirst { it.id == id }
        if (index != -1) {
            _tareas[index] = _tareas[index].copy(
                completada = !_tareas[index].completada
            )
        }
    }

    fun eliminarTarea(id: Int) {
        _tareas.removeAll { it.id == id }
    }
}