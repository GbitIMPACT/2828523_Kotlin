package com.example.myapplication.SistemaTransporteUrbano.interfaces

import com.example.myapplication.SistemaTransporteUrbano.classes.Vehiculo

interface Pasajero {
    fun abordar(vehiculo: Vehiculo): String;
    fun bajar(vehiculo: Vehiculo): String;
}