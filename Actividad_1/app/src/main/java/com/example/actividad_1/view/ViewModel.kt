package com.example.actividad_1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RegisterViewModel : ViewModel() {
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var termsAndConditions by mutableStateOf(false)
    var genres by mutableStateOf(listOf("Hombre", "Mujer", "Helicoptero apache", "Aguila Conde"))
    var genre by mutableStateOf("")
    fun save() { println() }
}