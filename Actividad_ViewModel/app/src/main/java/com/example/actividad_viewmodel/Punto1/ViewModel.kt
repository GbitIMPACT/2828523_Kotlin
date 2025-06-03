package com.example.actividad_viewmodel.Punto1
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class CounterViewModel : ViewModel() {

    var counter by mutableStateOf(0)
        private set


    fun increment() {
        counter++
    }
    fun decrement() {
        counter--
    }

}