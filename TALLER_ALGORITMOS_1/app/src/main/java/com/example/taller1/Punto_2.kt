// Definir constantes para los rangos de edad
const val NINO_MIN = 0
const val NINO_MAX = 12
const val ADOLESCENTE_MIN = 13
const val ADOLESCENTE_MAX = 17
const val ADULTO_MIN = 18
const val ADULTO_MAX = 64
const val MAYOR_MIN = 65

// Función para clasificar según la edad
fun clasificarEdad(edad: Int): String {
    return when (edad) {
        in NINO_MIN..NINO_MAX -> "Niño"
        in ADOLESCENTE_MIN..ADOLESCENTE_MAX -> "Adolescente"
        in ADULTO_MIN..ADULTO_MAX -> "Adulto"
        in MAYOR_MIN..Int.MAX_VALUE -> "Mayor"
        else -> "Edad inválida"
    }
}

fun main() {
    print("Ingresa varias edades separadas por espacios: ")
    val input = readLine()
    if (input != null) {
        // Dividimos la entrada en una lista de strings
        val edadesStr = input.split(" ")
        for (edadStr in edadesStr) {
            val edad = edadStr.toIntOrNull()
            if (edad != null) {
                val categoria = clasificarEdad(edad)
                println("Edad $edad: $categoria")
            } else {
                println("Edad '$edadStr' no es un número válido.")
            }
        }
    } else {
        println("No se ingresaron edades.")
    }
}