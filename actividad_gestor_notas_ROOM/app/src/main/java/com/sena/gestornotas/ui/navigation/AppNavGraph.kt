package com.sena.gestornotas.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sena.gestornotas.data.local.AppDatabase
import com.sena.gestornotas.data.local.repository.NotaRepository
import com.sena.gestornotas.ui.cliente.ClienteFormScreen
import com.sena.gestornotas.ui.cliente.ClienteListScreen
import com.sena.gestornotas.ui.nota.NotaFormScreen
import com.sena.gestornotas.ui.nota.NotaListScreen
import com.sena.gestornotas.viewmodel.ClienteViewModel
import com.sena.gestornotas.viewmodel.NotaViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember {
        NotaRepository(
            clienteDao = database.clienteDao(),
            notaDao = database.notaDao()
        )
    }

    NavHost(
        navController = navController,
        startDestination = "clientes"
    ) {
        composable("clientes") {
            val viewModel: ClienteViewModel = viewModel(
                factory = ClienteViewModel.Factory(repository)
            )

            ClienteListScreen(
                viewModel = viewModel,
                onNavigateToForm = {
                    navController.navigate("cliente_form")
                },
                onNavigateToEdit = { clienteId ->
                    navController.navigate("cliente_edit/$clienteId")
                },
                onNavigateToNotas = { clienteId ->
                    navController.navigate("notas/$clienteId")
                }
            )
        }
        composable("cliente_form") {
            val viewModel: ClienteViewModel = viewModel(
                factory = ClienteViewModel.Factory(repository)
            )

            ClienteFormScreen(
                viewModel = viewModel,
                clienteId = null,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Formulario de Cliente (Editar)
        composable(
            route = "cliente_edit/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: 0
            val viewModel: ClienteViewModel = viewModel(
                factory = ClienteViewModel.Factory(repository)
            )

            ClienteFormScreen(
                viewModel = viewModel,
                clienteId = clienteId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = "notas/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: 0
            val viewModel: NotaViewModel = viewModel(
                factory = NotaViewModel.Factory(repository, clienteId)
            )

            var clienteName by remember { mutableStateOf("") }
            LaunchedEffect(clienteId) {
                repository.getClienteById(clienteId)?.let { cliente ->
                    clienteName = cliente.nombre
                }
            }

            NotaListScreen(
                viewModel = viewModel,
                clienteName = clienteName,
                onNavigateToForm = {
                    navController.navigate("nota_form/$clienteId")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = "nota_form/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getInt("clienteId") ?: 0
            val viewModel: NotaViewModel = viewModel(
                factory = NotaViewModel.Factory(repository, clienteId)
            )

            NotaFormScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}