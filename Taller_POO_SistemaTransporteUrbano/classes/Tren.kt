package com.example.myapplication.SistemaTransporteUrbano.classes

import com.example.myapplication.SistemaTransporteUrbano.enums.Combustible
import com.example.myapplication.SistemaTransporteUrbano.interfaces.Conectable

class Tren(id: Int, velocidadMaxima: Int, cantidad: Int, edadMinima: Int = 18, combustible: Combustible) :  Vehiculo(id, velocidadMaxima, cantidad, edadMinima, combustible),
    Conectable {

    override fun conectar() {
        TODO("Not yet implemented")
    }

    override fun desconectar() {
        TODO("Not yet implemented")
    }
}