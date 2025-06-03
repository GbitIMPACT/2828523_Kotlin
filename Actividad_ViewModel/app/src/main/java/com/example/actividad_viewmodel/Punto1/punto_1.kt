package com.example.actividad_viewmodel.Punto1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto1() {
    val viewModel: CounterViewModel = viewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = viewModel.counter.toString(),
            fontSize = 72.sp,
            color = if (viewModel.counter % 2 == 0) Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            Button(
                onClick = { viewModel.decrement() },
                modifier = Modifier.size(80.dp)
            ) {
                Text("-", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { viewModel.increment() },
                modifier = Modifier.size(80.dp)
            ) {
                Text("+", fontSize = 32.sp)
            }
        }
    }
}