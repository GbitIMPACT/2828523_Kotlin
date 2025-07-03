package com.example.taller1

fun generarResumen(texto: String, palabraClave: String): Int {
    // Elimina signos de puntuación usando expresiones regulares
    val sinPuntuacion = texto.replace(Regex("[.,;:¡!¿?\"'()]"), "")

    // Divide el texto en palabras
    val palabras = sinPuntuacion.split(" ").filter { it.isNotBlank() }

    // Reemplaza palabras mayores a 6 letras por "[LARGA]"
    val palabrasReemplazadas = palabras.map { palabra ->
        if (palabra.length > 6) "[LARGA]" else palabra
    }

    // Une el texto resultante y lo muestra (opcional)
    val resumen = palabrasReemplazadas.joinToString(" ")
    println("Resumen: $resumen")

    // Cuenta las veces que aparece la palabra clave (sin distinción de mayúsculas)
    val cuentaClave = palabras.count { it.equals(palabraClave, ignoreCase = true) }

    return cuentaClave
}

fun main() {
    print("Ingresa un párrafo de texto: ")
    val texto = readLine() ?: ""

    print("Ingresa la palabra clave a buscar: ")
    val palabraClave = readLine() ?: ""

    val cantidad = generarResumen(texto, palabraClave)

    println("La palabra clave '$palabraClave' aparece $cantidad veces.")
}