package com.example.actividad_viewmodel.Punto9

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EmailValidacionViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set


    val esEmailValido: Boolean
        get() = validarEmail(email)


    val mostrarIndicador: Boolean
        get() = email.isNotEmpty()


    val mensajeEstado: String
        get() = when {
            email.isEmpty() -> "Ingresa tu correo electrónico"
            esEmailValido -> "✓ Correo válido"
            else -> "✗ Correo inválido"
        }

    fun actualizarEmail(nuevoEmail: String) {
        email = nuevoEmail
    }


    private fun validarEmail(email: String): Boolean {
        if (email.isEmpty()) return false


        return email.contains("@") &&
                email.contains(".") &&
                email.indexOf("@") < email.lastIndexOf(".") &&
                email.indexOf("@") > 0 &&
                email.lastIndexOf(".") < email.length - 1 &&
                !email.contains(" ")
    }


    fun limpiarEmail() {
        email = ""
    }
}