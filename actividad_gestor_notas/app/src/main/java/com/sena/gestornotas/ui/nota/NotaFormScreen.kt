package com.sena.gestornotas.ui.nota

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.gestornotas.viewmodel.NotaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotaFormScreen(
    viewModel: NotaViewModel,
    onNavigateBack: () -> Unit
) {
    var contenido by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Nota") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = contenido,
                onValueChange = {
                    contenido = it
                    showError = false
                },
                label = { Text("Contenido de la nota") },
                placeholder = { Text("Escribe aquí los detalles de la interacción...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 8,
                isError = showError
            )

            if (showError) {
                Text(
                    text = "El contenido no puede estar vacío",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        if (contenido.isNotBlank()) {
                            viewModel.saveNota(contenido.trim())
                            onNavigateBack()
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Información adicional
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "La fecha y hora se registrarán automáticamente al guardar la nota.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}