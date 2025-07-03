package com.example.myapplication.SistemaTransporteUrbano.classes

class Ruta(var paradas: MutableList<Parada>, var vehiculo: Vehiculo) {
    fun recorrerRuta(){
        for (parada in this.paradas){
            println("Va en la parada $parada");

            println("Lista de pasajeros: ${vehiculo.pasajeros}")
        }
    }

}