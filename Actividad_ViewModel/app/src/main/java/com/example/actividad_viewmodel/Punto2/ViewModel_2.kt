package com.example.actividad_viewmodel.Punto2

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // Estados para los campos
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    // Estados para los errores
    var emailError by mutableStateOf("")
        private set

    var passwordError by mutableStateOf("")
        private set

    // Estado para habilitar el botón
    var isLoginEnabled by mutableStateOf(false)
        private set

    // Actualizar email
    fun updateEmail(newEmail: String) {
        email = newEmail
        validateEmail()
        updateLoginButtonState()
    }

    // Actualizar contraseña
    fun updatePassword(newPassword: String) {
        password = newPassword
        validatePassword()
        updateLoginButtonState()
    }

    // Validar email
    private fun validateEmail() {
        emailError = when {
            email.isEmpty() -> "El correo es requerido"
            !email.contains("@") -> "El correo debe contener @"
            !email.contains(".com") -> "El correo debe contener .com"
            else -> ""
        }
    }

    // Validar contraseña
    private fun validatePassword() {
        passwordError = when {
            password.isEmpty() -> "La contraseña es requerida"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> ""
        }
    }

    // Actualizar estado del botón
    private fun updateLoginButtonState() {
        isLoginEnabled = emailError.isEmpty() &&
                passwordError.isEmpty() &&
                email.isNotEmpty() &&
                password.isNotEmpty()
    }

    // Función de login
    fun login() {
        if (isLoginEnabled) {
            // Aquí iría la lógica de login
            println("Login exitoso con: $email")
        }
    }
}