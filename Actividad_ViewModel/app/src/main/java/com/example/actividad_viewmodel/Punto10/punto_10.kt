package com.example.actividad_viewmodel.Punto10


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto10() {
    val viewModel: CargaViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Simulador de Carga",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )


        EstadoVisual(estado = viewModel.estadoActual)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.iniciarProceso() },
            enabled = viewModel.botonHabilitado,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = when (viewModel.estadoActual) {
                    CargaViewModel.EstadoBoton.INICIAL -> MaterialTheme.colorScheme.primary
                    CargaViewModel.EstadoBoton.CARGANDO -> Color.Gray
                    CargaViewModel.EstadoBoton.LISTO -> Color.Green
                }
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (viewModel.estadoActual == CargaViewModel.EstadoBoton.CARGANDO) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = viewModel.textoBoton,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = when (viewModel.estadoActual) {
                    CargaViewModel.EstadoBoton.INICIAL -> MaterialTheme.colorScheme.surfaceVariant
                    CargaViewModel.EstadoBoton.CARGANDO -> Color.Blue.copy(alpha = 0.1f)
                    CargaViewModel.EstadoBoton.LISTO -> Color.Green.copy(alpha = 0.1f)
                }
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = viewModel.mensajeEstado,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                color = when (viewModel.estadoActual) {
                    CargaViewModel.EstadoBoton.INICIAL -> Color.Gray
                    CargaViewModel.EstadoBoton.CARGANDO -> Color.Blue
                    CargaViewModel.EstadoBoton.LISTO -> Color.Green
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {

            }
        }

        if (viewModel.estadoActual == CargaViewModel.EstadoBoton.LISTO) {
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = { viewModel.reiniciar() }
            ) {
                Text("Reiniciar ahora")
            }
        }
    }



@Composable
fun EstadoVisual(estado: CargaViewModel.EstadoBoton) {
    val emoji = when (estado) {
        CargaViewModel.EstadoBoton.INICIAL -> "🔘"
        CargaViewModel.EstadoBoton.CARGANDO -> "⏳"
        CargaViewModel.EstadoBoton.LISTO -> "✅"
    }

    val colorFondo by animateColorAsState(
        targetValue = when (estado) {
            CargaViewModel.EstadoBoton.INICIAL -> Color.Gray.copy(alpha = 0.1f)
            CargaViewModel.EstadoBoton.CARGANDO -> Color.Blue.copy(alpha = 0.1f)
            CargaViewModel.EstadoBoton.LISTO -> Color.Green.copy(alpha = 0.1f)
        },
        label = "colorFondo"
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = colorFondo),
        modifier = Modifier.size(120.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = 48.sp
            )
        }
    }
}