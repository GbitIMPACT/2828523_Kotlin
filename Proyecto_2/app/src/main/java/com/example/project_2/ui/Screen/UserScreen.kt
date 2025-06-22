package com.example.project_2.ui.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.project_2.model.User
import com.example.project_2.viewmodel.UserViewModel

@Composable
fun UserList() {
    val viewModel: UserViewModel = hiltViewModel()

    val users by viewModel.users.collectAsState()
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var id: Int? by rememberSaveable { mutableStateOf(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            LazyColumn(modifier = Modifier.fillMaxSize(0.5f)) {
                items(items = users) {
                    Text(text = it.name)
                    Text(text = it.email)
                    Button(
                        onClick = {
                            viewModel.deleteUser(it.id)
                        },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) { Text(text = "✖️") }
                    Button(
                        onClick = {
                            id = it.id
                        },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) { Text(text = "🖋️") }
                }
            }
            if (id != null) {
                Column {
                    Text(
                        text = "Formulario Actulizacion",
                        modifier = Modifier.padding(vertical = 30.dp),
                        fontSize = 20.sp
                    )
                    OutlinedTextField(
                        label = { Text(text = "Actulizar name") },
                        value = name,
                        onValueChange = { name = it }
                    )
                    OutlinedTextField(
                        label = { Text(text = "Actulizar email") },
                        value = email,
                        onValueChange = { email = it }
                    )
                    Button(
                        onClick = {
                            viewModel.updateUser(id!!, name, email)
                            name = ""
                            email = ""
                        },
                        modifier = Modifier.padding(top = 24.dp)
                    ) {
                        Text(text = "Actualizar")
                    }
                }
            } else {
                Column {
                    Text(
                        text = "Formulario registro",
                        modifier = Modifier.padding(vertical = 30.dp),
                        fontSize = 20.sp
                    )
                    OutlinedTextField(
                        label = { Text(text = "Ingresar name") },
                        value = name,
                        onValueChange = { name = it }
                    )
                    OutlinedTextField(
                        label = { Text(text = "Ingresar email") },
                        value = email,
                        onValueChange = { email = it }
                    )
                    Button(
                        onClick = {
                            viewModel.addUser(name, email)
                            name = ""
                            email = ""
                        },
                        modifier = Modifier.padding(top = 24.dp)
                    ) {
                        Text(text = "Agregar")
                    }
                }
            }
        }
    }
}