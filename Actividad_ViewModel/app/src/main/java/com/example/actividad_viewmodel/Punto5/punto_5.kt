package com.example.actividad_viewmodel.Punto5
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto5() {
    val viewModel: PasswordStrengthViewModel = viewModel()
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fortaleza de Contraseña",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de contraseña
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Contraseña") },
            placeholder = { Text("Ingresa tu contraseña") },
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                TextButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(if (passwordVisible) "Ocultar" else "Mostrar")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Indicador de fortaleza con emoji
        if (viewModel.password.isNotEmpty()) {
            val animatedColor by animateColorAsState(
                targetValue = viewModel.strength.color,
                label = "color"
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = animatedColor.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewModel.strength.emoji,
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = viewModel.strength.label,
                        color = animatedColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProgressBar(
                progress = viewModel.strengthProgress,
                color = viewModel.strength.color
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${viewModel.password.length} caracteres",
                color = viewModel.strength.color,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
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
                    text = "Criterios para la contraseña:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CriteriaItem("Débil", "< 6 caracteres", Color.Red)
                CriteriaItem("Media", "6-9 caracteres", Color.Blue)
                CriteriaItem("Fuerte", "10-15 caracteres", Color.Green)
                CriteriaItem("Potente mi pana", "> 15 caracteres", Color.Magenta)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón limpiar
        if (viewModel.password.isNotEmpty()) {
            OutlinedButton(
                onClick = { viewModel.clearPassword() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpiar")
            }
        }
    }
}

@Composable
private fun ProgressBar(
    progress: Float,
    color: Color
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "progress"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Gray.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
    }
}

@Composable
private fun CriteriaItem(
    level: String,
    criteria: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "• $level",
            color = color,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = criteria,
            color = Color.Gray
        )
    }
}