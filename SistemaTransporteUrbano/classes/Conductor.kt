package com.example.myapplication.SistemaTransporteUrbano.classes

class Conductor(var nombre: String, var edad: Int, var autosAsignados: MutableList<Vehiculo> = mutableListOf<Vehiculo>()) {

    fun asignarVehiculo(vehiculo: Vehiculo){
        autosAsignados.add(vehiculo)
    }

    fun mostrarVehiculos() {
        autosAsignados.forEach { println(it.id) }
    }

}