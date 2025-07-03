package com.example.taller1

fun validarCredenciales(usuarios: Map<String, String>, usuario: String, contrasena: String): Boolean {
    val passCorrecta = usuarios[usuario]
    // Verifica usuario, contraseña y requisitos de la contraseña
    return passCorrecta == contrasena &&
            contrasena.length >= 6 &&
            contrasena.any { it.isDigit() }
}

fun main() {
    // Mapa de usuarios y contraseñas (usuario -> contraseña)
    val usuarios = mapOf(
        "David1" to "clave123",
        "David2" to "password9",
        "David3" to "abcde6"
    )

    var intentos = 0
    val maxIntentos = 3

    while (intentos < maxIntentos) {
        print("Usuario: ")
        val user = readLine() ?: ""
        print("Contraseña: ")
        val pass = readLine() ?: ""

        if (validarCredenciales(usuarios, user, pass)) {
            println("¡Login exitoso!")
            println("Bienvenido, $user!")
            return
        } else {
            println("Usuario o contraseña inválidos, o la contraseña no cumple los requisitos (Minimo 6 caracteres y un numero).")
            intentos++
        }
    }
    println("Has superado el número máximo de intentos.")
}