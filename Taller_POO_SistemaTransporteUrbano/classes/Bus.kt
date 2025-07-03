package com.example.myapplication.SistemaTransporteUrbano.classes

import com.example.myapplication.SistemaTransporteUrbano.enums.Combustible

open class Bus(
    id: Int,
    velocidadMaxima: Int,
    cantidad: Int,
    edadMinima: Int = 6,
    combustible: Combustible
) : Vehiculo(id, velocidadMaxima, cantidad, edadMinima, combustible) {
    override fun moverse(): String {
        return "Bus se mueve";
    }

    override fun detenerse(): String {
        return "Bus se detiene";
    }
}