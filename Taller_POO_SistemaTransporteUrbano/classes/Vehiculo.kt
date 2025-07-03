package com.example.myapplication.SistemaTransporteUrbano.classes

import com.example.myapplication.SistemaTransporteUrbano.enums.Combustible
import com.example.myapplication.SistemaTransporteUrbano.interfaces.Pasajero

abstract class Vehiculo(var id: Int,
                        var velocidadMaxima: Int,
                        var cantidad: Int,
                        var edadMinima: Int,
                        var combustible: Combustible,
                        var pasajeros: MutableList<Pasajero> = mutableListOf(),
                        var rutaAsignada: Ruta? = null
    ) {

    open fun moverse(): String {
        return "Clase vehiculo se mueve :D"
    }

    open fun detenerse(): String {
        return "Clase vehiculo Se detiene D:"
    }

    open fun addPasajero(pasajero: Pasajero){
        pasajeros?.add(pasajero)
    }
}