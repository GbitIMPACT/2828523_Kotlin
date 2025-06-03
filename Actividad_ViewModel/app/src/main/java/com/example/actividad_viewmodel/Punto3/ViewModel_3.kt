package com.example.actividad_viewmodel.Punto3


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PhoneViewModel : ViewModel() {
    var phoneNumber by mutableStateOf("")
    var isValid by mutableStateOf<Boolean?>(null)
    var validationMessage by mutableStateOf("")


    fun updatePhoneNumber(newNumber: String) {

        val filteredNumber = newNumber.filter { it.isDigit() }
        phoneNumber = filteredNumber


        if (isValid != null) {
            isValid = null
            validationMessage = ""
        }
    }

    fun validatePhoneNumber() {
        when {
            phoneNumber.isEmpty() -> {
                isValid = false
                validationMessage = "Por favor ingresa un número"
            }
            phoneNumber.length < 10 -> {
                isValid = false
                validationMessage = "El número debe tener 10 dígitos (faltan ${10 - phoneNumber.length})"
            }
            phoneNumber.length > 10 -> {
                isValid = false
                validationMessage = "Recuerda, el número debe tener 10 dígitos (sobran ${phoneNumber.length - 10})"
            }
            phoneNumber.length == 10 -> {
                isValid = true
                validationMessage = "El número ingresado es válido!"
            }
        }
    }


    fun clearPhoneNumber() {
        phoneNumber = ""
        isValid = null
        validationMessage = ""
    }
}