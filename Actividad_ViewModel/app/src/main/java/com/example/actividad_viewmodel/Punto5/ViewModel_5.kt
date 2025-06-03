package com.example.actividad_viewmodel.Punto5
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class PasswordStrengthViewModel : ViewModel() {
    var password by mutableStateOf("")

    // Calcular fortaleza
    val strength: PasswordStrength
        get() = when {
            password.length < 6 -> PasswordStrength.WEAK
            password.length < 10 -> PasswordStrength.MEDIUM
            else -> PasswordStrength.STRONG
        }

    val strengthProgress: Float
        get() = when {
            password.isEmpty() -> 0f
            password.length < 6 -> password.length / 6f * 0.33f
            password.length < 10 -> 0.33f + ((password.length - 6) / 4f * 0.33f)
            else -> minOf(1f, 0.66f + ((password.length - 10) / 5f * 0.34f))
        }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun clearPassword() {
        password = ""
    }
}

enum class PasswordStrength(
    val label: String,
    val color: Color,
    val emoji: String
) {
    WEAK("Débil", Color.Red, "😒"),
    MEDIUM("Media", Color(0xFFFFA000), "😐"),
    STRONG("Fuerte", Color(0xFF4CAF50), "👌")

}