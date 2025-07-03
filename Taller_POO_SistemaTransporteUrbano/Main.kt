package com.example.myapplication.SistemaTransporteUrbano

import com.example.myapplication.SistemaTransporteUrbano.classes.Adulto
import com.example.myapplication.SistemaTransporteUrbano.classes.Bus
import com.example.myapplication.SistemaTransporteUrbano.classes.Conductor
import com.example.myapplication.SistemaTransporteUrbano.classes.Niño
import com.example.myapplication.SistemaTransporteUrbano.classes.Parada
import com.example.myapplication.SistemaTransporteUrbano.classes.Ruta
import com.example.myapplication.SistemaTransporteUrbano.classes.Tren
import com.example.myapplication.SistemaTransporteUrbano.enums.Combustible

fun main() {
    val bus = Bus(1, 100, 40, 6, Combustible.GASOLINA)
    val tren = Tren(2, 200, 80, 18, Combustible.ELETRICO)

    val conductor1 = Conductor("Pedro", 45)
    conductor1.asignarVehiculo(bus)

    CentroControl.registrarVehiculo(bus)
    CentroControl.registrarVehiculo(tren)
    CentroControl.registrarConductor(conductor1)

    val niño = Niño("Luca", 8)
    val adulto = Adulto("María", 30)

    if (niño.abordar(bus) == "Se montó") {
        bus.addPasajero(niño)
    }

    CentroControl.registrarPasajeros(bus.pasajeros.size)

    val rutaBus = Ruta(mutableListOf(Parada("A"), Parada("B"), Parada("C")), bus)
    val rutaTren = Ruta(mutableListOf(Parada("A"), Parada("B"), Parada("C")), tren)

    println("\n=== Recorrido del BUS ===")
    rutaBus.recorrerRuta()

    println("\n=== Recorrido del TREN ===")
    for (parada in rutaTren.paradas) {
        println("Tren en la parada: ${parada.nombre}")

        if (parada.nombre == "B" && adulto.abordar(tren) == "Se montó") {
            tren.addPasajero(adulto)
            CentroControl.registrarPasajeros(1)
            println("Adulto María abordó el tren en la parada B")
        }

        println("Pasajeros a bordo del tren:")
        if (tren.pasajeros.isEmpty()) {
            println("- Ninguno")
        } else {
            tren.pasajeros.forEach { println("- $it") }
        }
    }

    CentroControl.rutaFinalizada()
    CentroControl.mostrarEstadisticas()
}
