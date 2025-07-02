package com.example.myapplication.SistemaTransporteUrbano.classes

import com.example.myapplication.SistemaTransporteUrbano.interfaces.Pasajero

class Adulto(var nombre: String, var edad: Int) : Pasajero {

    init {
        require(edad >= 18){
            "Un adulto debe ser mayor a 18 años"
        }
    }

    override fun abordar(vehiculo: Vehiculo): String {
        if(vehiculo.edadMinima > this.edad ){
            return "No se puede montar";
        }

        return "Se montó";
    }

    override fun bajar(vehiculo: Vehiculo): String {
        return "Se bajó";
    }

    override fun toString(): String {
        return "Adulto(nombre='$nombre', edad=$edad)"
    }


}