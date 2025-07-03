package com.sena.gestornotas.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes ORDER BY nombre ASC")
    fun getAllClientes(): Flow<List<Cliente>>

    @Query("SELECT * FROM clientes WHERE id = :clienteId")
    suspend fun getClienteById(clienteId: Int): Cliente?

    @Insert
    suspend fun insertCliente(cliente: Cliente)

    @Update
    suspend fun updateCliente(cliente: Cliente)

    @Delete
    suspend fun deleteCliente(cliente: Cliente)

    @Transaction
    @Query("SELECT * FROM clientes WHERE id = :clienteId")
    suspend fun getClienteConNotas(clienteId: Int): ClienteConNotas?
}