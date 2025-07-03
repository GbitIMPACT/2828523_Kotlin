package com.example.taller1

fun main() {
    // Lista de oraciones
    val oraciones = listOf(
        "Tres tristes tigres tragaban trigo de un trigal",
        "Kotlin con Android",
        "Reconocer es una palabra palíndroma"
    )

    for ((i, oracion) in oraciones.withIndex()) {
        println("Oración ${i + 1}: \"$oracion\"")


        val palabras = oracion.split(" ").filter { it.isNotBlank() }

        println("Cantidad de palabras: ${palabras.size}")

        var maxLetras = 0
        var palabraPalindromo = ""
        for (palabra in palabras) {
            val limpia = palabra.filter { it.isLetter() }
            if (limpia.length > maxLetras) maxLetras = limpia.length

            if (limpia.length > 1 && limpia.lowercase() == limpia.lowercase().reversed()) {
                palabraPalindromo = limpia
            }
        }
        println("Letras de la palabra más larga: $maxLetras")
        
        if (palabraPalindromo.isNotEmpty()) {
            println("¡Palíndromo encontrado!: $palabraPalindromo")
        } else {
            println("No hay palabras palíndromas.")
        }
        println()
    }
}