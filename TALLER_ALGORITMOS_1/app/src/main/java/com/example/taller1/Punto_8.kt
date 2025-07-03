package com.example.taller1

fun main() {
    var calificaciones: List<Int>
    do {
        print("Ingresa las calificaciones separadas por espacio: ")
        val entrada = readLine() ?: ""
        calificaciones = entrada
            .split(" ")
            .mapNotNull { it.toIntOrNull() }

        if (calificaciones.isEmpty()) {
            println("No se ingresaron calificaciones válidas. Intenta nuevamente.\n")
        }

    } while (calificaciones.isEmpty())

    // Filtrar notas aprobadas (> 60)
    val aprobadas = calificaciones.filter { it > 60 }
    println("Notas aprobadas (>60): $aprobadas")

    // Calcular promedio, máxima y mínima
    val promedio = calificaciones.average()
    val maxima = calificaciones.maxOrNull() ?: 0
    val minima = calificaciones.minOrNull() ?: 0
    println("Promedio: %.2f".format(promedio))
    println("Máxima: $maxima")
    println("Mínima: $minima")

    // Agrupar en categorías usando map y when
    println("\nReporte de categorias:")
    calificaciones.forEach { nota ->
        val categoria = when {
            nota >= 90 -> "Excelente"
            nota >= 75 -> "Bueno"
            nota >= 61 -> "Regular"
            else -> "Malo"
        }
        println("Nota $nota: $categoria")
    }
}