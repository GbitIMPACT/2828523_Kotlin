package com.example.taller1

// Lista mutable para guardar los nombres
val listaNombres = mutableListOf<String>()

// Función para agregar nombre sin duplicados (ignorando mayúsculas)
fun agregarNombre(nombre: String): Boolean {
    val existe = listaNombres.any { it.equals(nombre, ignoreCase = true) }
    return if (!existe) {
        listaNombres.add(nombre)
        true
    } else {
        false
    }
}

// Función para mostrar todos los nombres ordenados alfabéticamente
fun mostrarNombres() {
    val nombresOrdenados = listaNombres.sortedBy { it.lowercase() }
    println("Nombres registrados:")
    for (n in nombresOrdenados) {
        println(n)
    }
}

// Función para buscar si un nombre existe (ignorando mayúsculas)
fun existeNombre(nombre: String): Boolean {
    return listaNombres.any { it.equals(nombre, ignoreCase = true) }
}

fun main() {
    while (true) {
        println("\n1. Agregar nombre\n2. Mostrar nombres\n3. Buscar nombre\n4. Salir")
        print("Elige una opción: ")
        val opcion = readLine() ?: "4"
        when (opcion) {
            "1" -> {
                print("Ingresa el nombre a agregar: ")
                val nombre = readLine() ?: ""
                if (nombre.isNotBlank()) {
                    if (agregarNombre(nombre)) {
                        println("Nombre agregado correctamente.")
                    } else {
                        println("Ese nombre ya está registrado.")
                    }
                }
            }
            "2" -> mostrarNombres()
            "3" -> {
                print("Ingresa el nombre a buscar: ")
                val nombre = readLine() ?: ""
                if (existeNombre(nombre)) {
                    println("El nombre '$nombre' SI existe en el registro.")
                } else {
                    println("El nombre '$nombre' NO existe en el registro.")
                }
            }
            "4" -> {
                println("Saliendo del sistema.")
                break
            }
            else -> println("Opción no válida.")
        }
    }
}