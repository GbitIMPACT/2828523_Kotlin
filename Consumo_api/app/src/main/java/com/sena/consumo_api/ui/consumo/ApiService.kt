package com.sena.consumo_api.ui.consumo
import com.sena.consumo_api.ui.model.Usuario
import retrofit2.Response
import retrofit2.http.*
interface ApiService {
    @GET('usuario')
    suspend fun getUsuarios(): List<Usuario>

    @POST('usuario')
    suspend fun crearUsuario(@Body usuario: Usuario): Response<ApiResponse>

    @PUT('usuario/{id}')
    suspend fun actualizarUsuario@Path("id") id: Int, @Body usuario: Usuario):Response0
}