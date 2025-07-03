package com.sena.gestornotas.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.gestornotas.viewmodel.ClienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteFormScreen(
    viewModel: ClienteViewModel,
    clienteId: Int? = null,
    onNavigateBack: () -> Unit
) {
    val isEditMode = clienteId != null
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(isEditMode) }

    LaunchedEffect(clienteId) {
        if (clienteId != null) {
            viewModel.loadCliente(clienteId)
        }
    }

    val clienteToEdit by viewModel.clienteToEdit.collectAsStateWithLifecycle()

    LaunchedEffect(clienteToEdit) {
        clienteToEdit?.let { cliente ->
            nombre = cliente.nombre
            correo = cliente.correo
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (isEditMode) "Editar Cliente" else "Nuevo Cliente")
                }
            )
        }
    ) { paddingValues ->
        if (isLoading && isEditMode) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

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
                            if (nombre.isNotBlank() && correo.isNotBlank()) {
                                if (isEditMode && clienteId != null) {
                                    viewModel.updateCliente(clienteId, nombre, correo)
                                } else {
                                    viewModel.saveCliente(nombre, correo)
                                }
                                onNavigateBack()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isEditMode) "Actualizar" else "Guardar")
                    }
                }
            }
        }
    }
}