package com.example.myapplication.SistemaTransporteUrbano

import com.example.myapplication.SistemaTransporteUrbano.classes.Conductor
import com.example.myapplication.SistemaTransporteUrbano.classes.Vehiculo

object CentroControl {

    val vehiculosActivos = mutableListOf<Vehiculo>()

    val conductores = mutableListOf<Conductor>()

    var rutasCompletadas = 0

    var totalPasajerosTransportados = 0
    val historialEventos = mutableListOf<String>()

    fun registrarVehiculo(v: Vehiculo) {
        vehiculosActivos.add(v)
        historialEventos.add("Vehículo con ID ${v.id} registrado como activo.")
    }

    fun registrarConductor(c: Conductor) {
        conductores.add(c)
        historialEventos.add("Conductor ${c.nombre} registrado.")
    }

    fun rutaFinalizada() {
        rutasCompletadas++
        historialEventos.add("Ruta completada. Total acumulado: $rutasCompletadas")
    }

    fun registrarPasajeros(cantidad: Int) {
        totalPasajerosTransportados += cantidad
        historialEventos.add("Se registraron $cantidad pasajeros nuevos. Total: $totalPasajerosTransportados")
    }

    fun mostrarEstadisticas() {
        println("=== Estadísticas del Centro de Control ===")
        println("Vehículos activos: ${vehiculosActivos.size}")
        println("Conductores registrados: ${conductores.size}")
        println("Rutas completadas: $rutasCompletadas")
        println("Pasajeros transportados: $totalPasajerosTransportados")
        println("Historial de eventos:")
        historialEventos.forEach { println("- $it") }
    }
}
