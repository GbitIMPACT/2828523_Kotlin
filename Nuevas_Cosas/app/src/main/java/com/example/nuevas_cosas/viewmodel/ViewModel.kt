package com.example.nuevas_cosas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {  // Cambia el nombre y extiende de ViewModel
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var pass by mutableStateOf("")
    var termsAndConditions by mutableStateOf(false)
    var genres by mutableStateOf(listOf("Hombre", "Mujer", "Helicoptero apache", "Aguila Conde"))
    var genre by mutableStateOf("")

    fun save() {
        // Your save logic here
        println("Guardando: $name, $age, $genre")
    }
}