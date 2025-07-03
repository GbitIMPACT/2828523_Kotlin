package com.sena.gestornotas.ui.nota

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.gestornotas.data.local.Nota
import com.sena.gestornotas.viewmodel.NotaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotaListScreen(
    viewModel: NotaViewModel,
    clienteName: String,
    onNavigateToForm: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val notas by viewModel.notas.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notas de $clienteName") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToForm
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Nota")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(notas) { nota ->
                NotaItem(
                    nota = nota,
                    onDeleteClick = { viewModel.deleteNota(nota) }
                )
            }

            if (notas.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay notas para este cliente",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotaItem(
    nota: Nota,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = nota.contenido,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = nota.fecha,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.padding(0.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar nota",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}