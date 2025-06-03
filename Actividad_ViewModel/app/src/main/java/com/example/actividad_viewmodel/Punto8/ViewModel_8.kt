package com.example.actividad_viewmodel.Punto8

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class TextoLimiteViewModel : ViewModel() {
    private val limiteCaracteres = 100

    var texto by mutableStateOf("")


    val contadorTexto: String
        get() = "${texto.length}/$limiteCaracteres"

    val colorContador: Color
        get() = when {
            texto.length > limiteCaracteres -> Color.Red
            texto.length >= limiteCaracteres * 0.9 -> Color(0xFFFFA000) // Naranja de advertencia
            else -> Color.Gray
        }

    val excedeLimite: Boolean
        get() = texto.length > limiteCaracteres


    val caracteresRestantes: Int
        get() = limiteCaracteres - texto.length


    val porcentajeUsado: Float
        get() = (texto.length.toFloat() / limiteCaracteres).coerceIn(0f, 1f)


    fun actualizarTexto(nuevoTexto: String) {
        texto = nuevoTexto
    }


    fun limpiarTexto() {
        texto = ""
    }
}