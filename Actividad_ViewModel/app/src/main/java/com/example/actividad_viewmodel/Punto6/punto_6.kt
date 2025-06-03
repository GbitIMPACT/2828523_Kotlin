package com.example.actividad_viewmodel.Punto6
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto6() {
    val viewModel: RegistroViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro con validaciones 👍",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 24.dp, top = 48.dp)

        )

        CampoFormulario(
            valor = viewModel.nombre,
            onCambio = { viewModel.actualizarNombre(it) },
            etiqueta = "Nombre",
            error = viewModel.nombreError,
            placeholder = "Ingresa tu nombre completo, minimo 3 letras"
        )

        Spacer(modifier = Modifier.height(16.dp))


        CampoFormulario(
            valor = viewModel.correo,
            onCambio = { viewModel.actualizarCorreo(it) },
            etiqueta = "Correo electrónico",
            error = viewModel.correoError,
            placeholder = "Ingresa un correo valido con @ y .com",
            tipoTeclado = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))


        CampoFormulario(
            valor = viewModel.password,
            onCambio = { viewModel.actualizarPassword(it) },
            etiqueta = "Contraseña",
            error = viewModel.passwordError,
            placeholder = "Mínimo 6 caracteres sin distinguir",
            esPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        CampoFormulario(
            valor = viewModel.confirmarPassword,
            onCambio = { viewModel.actualizarConfirmarPassword(it) },
            etiqueta = "Confirmar contraseña",
            error = viewModel.confirmarPasswordError,
            placeholder = "Repite tu contraseña",
            esPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = { viewModel.registrar() },
            enabled = viewModel.isFormularioValido,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Registrarse",
                fontSize = 16.sp
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        if (!viewModel.isFormularioValido) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Red.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = "Debes completar todos los campos correctamente",
                    color = Color.Red,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 14.sp
                )
            }
        } else {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Green.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = "Formulario válido",
                    color = Color.Green,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CampoFormulario(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String,
    placeholder: String,
    esPassword: Boolean = false,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    Column {
        OutlinedTextField(
            value = valor,
            onValueChange = onCambio,
            label = { Text(etiqueta) },
            placeholder = { Text(placeholder) },
            isError = error.isNotEmpty(),
            visualTransformation = if (esPassword)
                PasswordVisualTransformation()
            else
                androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (error.isNotEmpty()) Color.Red else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (error.isNotEmpty()) Color.Red else MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}