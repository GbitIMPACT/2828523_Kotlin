fun main() {
    val frutasFavoritas = setOf("Manzana", "Banana", "Naranja", "Uva")

    val frutasDisponibles = mutableSetOf("Naranja", "Pera", "Piña", "Manzana")

    println("Frutas favoritas: $frutasFavoritas")
    println("Frutas disponibles: $frutasDisponibles")

    val union = frutasFavoritas union frutasDisponibles
    println("Unión: $union")

    val interseccion = frutasFavoritas intersect frutasDisponibles
    println("Intersección: $interseccion")

    val diferencia = frutasFavoritas subtract frutasDisponibles
    println("Diferencia (favoritas que no están disponibles): $diferencia")

    print("Agrega una nueva fruta a las disponibles: ")
    val nuevaFruta = readLine()?.trim()
    if (!nuevaFruta.isNullOrBlank()) {
        frutasDisponibles.add(nuevaFruta)
        println("Frutas disponibles actualizadas: $frutasDisponibles")
        // Verificar si la fruta está en las favoritas
        if (nuevaFruta in frutasFavoritas) {
            println("$nuevaFruta es una fruta favorita.")
        } else {
            println("$nuevaFruta NO es una fruta favorita.")
        }
    } else {
        println("No se agregó ninguna fruta.")
    }
}