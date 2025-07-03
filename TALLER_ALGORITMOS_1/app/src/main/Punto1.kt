fun main() {
    print("Ingresa una frase: ")
    val frase = readLine() ?: ""

    // Separar la frase en palabras
    val palabras = frase.split(" ").filter { it.isNotBlank() }

    // Contar cuántas palabras hay
    val cantidadPalabras = palabras.size

    // Definir las vocales
    val vocales = listOf('a', 'e', 'i', 'o', 'u')

    // Contar cuántas palabras empiezan con vocal
    var palabrasConVocal = 0
    for (palabra in palabras) {
        // Verificamos que la palabra no esté vacía y extraemos la primera letra en minúscula
        if (palabra.isNotEmpty() && palabra[0].lowercaseChar() in vocales) {
            palabrasConVocal++
        }
    }

    // Crear una lista con cada palabra en minúscula, recortada a los primeros 4 caracteres
    val listaRecortada = mutableListOf<String>()
    for (palabra in palabras) {
        val palabraMinus = palabra.toLowerCase()
        val recortada = if (palabraMinus.length >= 4) palabraMinus.substring(0, 4) else palabraMinus
        listaRecortada.add(recortada)
    }

    // Mostrar resultados
    println("Cantidad de palabras: $cantidadPalabras")
    println("Palabras que comienzan con vocal: $palabrasConVocal")
    println("Lista de palabras en minúscula y recortadas: $listaRecortada")
}