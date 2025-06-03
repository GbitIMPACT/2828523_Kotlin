package com.example.actividad_viewmodel.Punto6
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegistroViewModel : ViewModel() {
    var nombre by mutableStateOf("")

    var correo by mutableStateOf("")

    var password by mutableStateOf("")

    var confirmarPassword by mutableStateOf("")


    // Aqui van los mensajes de error
    var nombreError by mutableStateOf("")


    var correoError by mutableStateOf("")


    var passwordError by mutableStateOf("")


    var confirmarPasswordError by mutableStateOf("")


    val isFormularioValido: Boolean
        get() = nombre.isNotEmpty() &&
                correo.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmarPassword.isNotEmpty() &&
                nombreError.isEmpty() &&
                correoError.isEmpty() &&
                passwordError.isEmpty() &&
                confirmarPasswordError.isEmpty()


    fun actualizarNombre(nuevoNombre: String) {
        nombre = nuevoNombre
        validarNombre()
    }

    fun actualizarCorreo(nuevoCorreo: String) {
        correo = nuevoCorreo
        validarCorreo()
    }

    fun actualizarPassword(nuevaPassword: String) {
        password = nuevaPassword
        validarPassword()
        // Revalidar confirmación cuando cambia la contraseña
        if (confirmarPassword.isNotEmpty()) {
            validarConfirmarPassword()
        }
    }

    fun actualizarConfirmarPassword(nuevaConfirmacion: String) {
        confirmarPassword = nuevaConfirmacion
        validarConfirmarPassword()
    }

//mas de validaciones para cada campo
    private fun validarNombre() {
        nombreError = when {
            nombre.isEmpty() -> "El nombre es requerido"
            nombre.length < 3 -> "El nombre debe tener al menos 3 caracteres"
            else -> ""
        }
    }

    private fun validarCorreo() {
        correoError = when {
            correo.isEmpty() -> "El correo es requerido"
            !correo.contains("@") -> "El correo debe contener @"
            !correo.contains(".") -> "El correo debe contener un punto"
            else -> ""
        }
    }

    private fun validarPassword() {
        passwordError = when {
            password.isEmpty() -> "La contraseña es requerida"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> ""
        }
    }

    private fun validarConfirmarPassword() {
        confirmarPasswordError = when {
            confirmarPassword.isEmpty() -> "Confirma tu contraseña"
            confirmarPassword != password -> "Las contraseñas no coinciden"
            else -> ""
        }
    }

    fun registrar() {
        if (isFormularioValido) {
            println("Registro exitoso: $nombre - $correo")
        }
    }
}