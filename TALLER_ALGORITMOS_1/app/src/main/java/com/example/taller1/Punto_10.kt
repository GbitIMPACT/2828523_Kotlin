package com.example.taller1

fun main() {
    val libros = mutableListOf<Map<String, Any>>()

    fun agregarLibro() {
        print("Titulo: ")
        val titulo = readLine() ?: ""
        print("Autor: ")
        val autor = readLine() ?: ""
        print("Numero de páginas: ")
        val paginas = readLine()?.toIntOrNull() ?: 0
        val libro = mapOf(
            "titulo" to titulo,
            "autor" to autor,
            "paginas" to paginas
        )
        libros.add(libro)
        println("Libro agregado.\n")
    }

    fun listarPorAutor() {
        print("Autor a buscar: ")
        val autor = readLine() ?: ""
        val encontrados = libros.filter { it["autor"]?.toString().equals(autor, ignoreCase = true) }
        if (encontrados.isEmpty()) {
            println("No hay libros de ese autor.\n")
        } else {
            println("Libros de $autor:")
            for (libro in encontrados) {
                println("Título: ${libro["titulo"]}, Páginas: ${libro["paginas"]}")
            }
            println()
        }
    }

    fun buscarPorTitulo() {
        print("Tiutulo a buscar: ")
        val titulo = readLine() ?: ""
        val libro = libros.find { it["titulo"]?.toString().equals(titulo, ignoreCase = true) }
        if (libro != null) {
            println("Libro encontrado: Titulo: ${libro["titulo"]}, Autor: ${libro["autor"]}, Páginas: ${libro["paginas"]}\n")
        } else {
            println("No se encontro un libro con ese titulo.\n")
        }
    }

    fun promedioPaginas() {
        if (libros.isEmpty()) {
            println("No hay libros registrados.\n")
            return
        }
        val promedio = libros.map { it["paginas"] as Int }.average()
        println("Promedio de paginas de todos los libros: %.2f\n".format(promedio))
    }

    while (true) {
        println("1. Agregar libro")
        println("2. Listar libros por autor")
        println("3. Buscar libro por titulo")
        println("4. Mostrar promedio de paginas")
        println("5. Salir")
        print("Elige una opcion mister: ")
        when (readLine()) {
            "1" -> agregarLibro()
            "2" -> listarPorAutor()
            "3" -> buscarPorTitulo()
            "4" -> promedioPaginas()
            "5" -> { println("Saliendo."); break }
            else -> println("Opción no válida.\n")
        }
    }
}