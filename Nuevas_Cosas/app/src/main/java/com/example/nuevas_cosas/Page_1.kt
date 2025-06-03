package com.example.nuevas_cosas

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun OrientationScreen(){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    BoxWithConstraints {
        if (screenWidth > 700.dp) {
            print("Es una computadora")
        } else if (screenWidth > 600.dp) {
            print("Es una tablet")
        }else{
            print("Es un movil")
        }
    }
}

@Composable
fun textito(texto: String){
    Text(texto)
}

@Composable
fun Home(navController: NavHostController) {
    val configuration = LocalConfiguration.current

    var texto by remember { mutableStateOf("") }
    var data by rememberSaveable { mutableStateOf("") }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Hola desde ventana 1")

            TextField(
                value = data,
                onValueChange = { data = it },
                label = { Text("Nombre") },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("segundapagina/$data") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                        Color.Magenta
                    else
                        Color.Red
                )
            ) {
                Text("Ir a la segunda pagina")
            }
        }
    }
}
