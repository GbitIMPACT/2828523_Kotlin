package com.example.taller1

fun main() {
    print("Ingresa una frase: ")
    val frase = readLine() ?: ""

    val palabras = frase.split(" ").filter { it.isNotBlank() }
    val cantidadPalabras = palabras.size
    val vocales = listOf('a', 'e', 'i', 'o', 'u')
    var palabrasConVocal = 0
    for (palabra in palabras) {
        if (palabra.isNotEmpty() && palabra[0].lowercaseChar() in vocales) {
            palabrasConVocal++
        }
    }

    val listaRecortada = mutableListOf<String>()
    for (palabra in palabras) {
        val palabraMinus = palabra.toLowerCase()
        val recortada = if (palabraMinus.length >= 4) palabraMinus.substring(0, 4) else palabraMinus
        listaRecortada.add(recortada)
    }

    println("Cantidad de palabras: $cantidadPalabras")
    println("Palabras que comienzan con vocal: $palabrasConVocal")
    println("Lista de palabras en minúscula y recortadas: $listaRecortada")
}