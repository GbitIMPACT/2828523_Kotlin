
val inventario = mutableMapOf<String, Int>()


fun agregarProducto(nombre: String, cantidad: Int) {
    val actual = inventario[nombre] ?: 0
    inventario[nombre] = actual + cantidad
}


fun eliminarAgotados() {
    val agotados = inventario.filter { it.value <= 0 }.keys
    for (producto in agotados) {
        inventario.remove(producto)
    }
}

fun mostrarMaxMin() {
    if (inventario.isNotEmpty()) {
        val mayor = inventario.entries.maxByOrNull { it.value }
        val menor = inventario.entries.minByOrNull { it.value }
        println("Producto con mayor cantidad: ${mayor?.key} (${mayor?.value})")
        println("Producto con menor cantidad: ${menor?.key} (${menor?.value})")
    } else {
        println("El inventario está vacío.")
    }
}
fun mostrarInventario() {
    if (inventario.isEmpty()) {
        println("El inventario está vacío.")
    } else {
        println("Inventario actual:")
        for ((producto, cantidad) in inventario) {
            println("- $producto: $cantidad")
        }
    }
}

fun main() {
    while (true) {
        println("\n1. Agregar/actualizar producto")
        println("2. Eliminar agotados")
        println("3. Mostrar mayor y menor")
        println("4. Ver inventario")
        println("5. Salir")
        print("Elige una opción: ")
        val op = readLine()
        when (op) {
            "1" -> {
                print("Nombre del producto: ")
                val nombre = readLine() ?: ""
                print("Cantidad a agregar: ")
                val cantidad = readLine()?.toIntOrNull() ?: 0
                agregarProducto(nombre, cantidad)
                println("Producto actualizado.")
            }
            "2" -> {
                eliminarAgotados()
                println("Productos agotados eliminados.")
            }
            "3" -> mostrarMaxMin()
            "4" -> mostrarInventario()
            "5" -> {
                println("Saliendo del inventario.")
                break
            }
            else -> println("Opción no válida.")
        }
    }
}