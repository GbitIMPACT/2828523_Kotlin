package com.example.myapplication.SistemaTransporteUrbano.classes

import com.example.myapplication.SistemaTransporteUrbano.interfaces.Pasajero

class Niño(var nombre: String, var edad: Int) : Pasajero {
    init {
        require(edad >= 1){
            "La edad no puede ser negativa ni 0"
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
        return "Niño(nombre='$nombre', edad=$edad)"
    }


}