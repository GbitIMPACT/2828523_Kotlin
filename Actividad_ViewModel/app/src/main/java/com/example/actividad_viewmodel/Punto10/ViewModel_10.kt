package com.example.actividad_viewmodel.Punto10

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CargaViewModel : ViewModel() {
    enum class EstadoBoton {
        INICIAL,
        CARGANDO,
        LISTO
    }

    var estadoActual by mutableStateOf(EstadoBoton.INICIAL)



    val textoBoton: String
        get() = when (estadoActual) {
            EstadoBoton.INICIAL -> "Iniciar proceso"
            EstadoBoton.CARGANDO -> "Cargando..."
            EstadoBoton.LISTO -> "¡Listo! ✓"
        }


    val botonHabilitado: Boolean
        get() = estadoActual == EstadoBoton.INICIAL


    val mensajeEstado: String
        get() = when (estadoActual) {
            EstadoBoton.INICIAL -> "Presiona el botón para comenzar"
            EstadoBoton.CARGANDO -> "Por favor espera..."
            EstadoBoton.LISTO -> "¡Proceso completado con éxito!"
        }


    fun iniciarProceso() {
        viewModelScope.launch {

            estadoActual = EstadoBoton.CARGANDO


            delay(7000)


            estadoActual = EstadoBoton.LISTO


            delay(3000)
            estadoActual = EstadoBoton.INICIAL
        }
    }


    fun reiniciar() {
        estadoActual = EstadoBoton.INICIAL
    }
}