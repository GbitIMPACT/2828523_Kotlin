package com.example.actividad_viewmodel.Punto7
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto7() {
    val viewModel: TareasViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Tareas",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = viewModel.nuevaTarea,
                onValueChange = { viewModel.actualizarNuevaTarea(it) },
                label = { Text("Nueva tarea") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.agregarTarea() },
                enabled = viewModel.nuevaTarea.isNotEmpty()
            ) {
                Text("Agregar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.tareas.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "No hay tareas. ¡Agrega una!",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.tareas) { tarea ->
                    TareaItem(
                        tarea = tarea,
                        onCambiarEstado = { viewModel.cambiarEstadoTarea(tarea.id) },
                        onEliminar = { viewModel.eliminarTarea(tarea.id) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val completadas = viewModel.tareas.count { it.completada }
        val total = viewModel.tareas.size

        if (total > 0) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Green.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = "Completadas: $completadas de $total",
                    modifier = Modifier.padding(12.dp),
                    color = Color.Green
                )
            }
        }
    }
}

@Composable
fun TareaItem(
    tarea: Tarea,
    onCambiarEstado: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (tarea.completada)
                Color.Green.copy(alpha = 0.1f)
            else
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (tarea.completada) 0.dp else 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = { onCambiarEstado() }
            )
            Text(
                text = tarea.titulo,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                textDecoration = if (tarea.completada)
                    TextDecoration.LineThrough
                else
                    TextDecoration.None,
                color = if (tarea.completada)
                    Color.Gray
                else
                    MaterialTheme.colorScheme.onSurface
            )

            TextButton(
                onClick = onEliminar
            ) {
                Text("Eliminar", color = Color.Red)
            }
        }
    }
}