package com.sena.gestornotas.data.local.repository

import com.sena.gestornotas.data.local.*
import kotlinx.coroutines.flow.Flow

class NotaRepository(
    private val clienteDao: ClienteDao,
    private val notaDao: NotaDao
) {
    fun getAllClientes(): Flow<List<Cliente>> = clienteDao.getAllClientes()

    suspend fun insertCliente(cliente: Cliente) = clienteDao.insertCliente(cliente)

    suspend fun updateCliente(cliente: Cliente) = clienteDao.updateCliente(cliente)

    suspend fun deleteCliente(cliente: Cliente) = clienteDao.deleteCliente(cliente)

    suspend fun getClienteById(id: Int): Cliente? = clienteDao.getClienteById(id)

    suspend fun getClienteConNotas(clienteId: Int): ClienteConNotas? =
        clienteDao.getClienteConNotas(clienteId)

    fun getNotasByClienteId(clienteId: Int): Flow<List<Nota>> =
        notaDao.getNotasByClienteId(clienteId)

    suspend fun insertNota(nota: Nota) = notaDao.insertNota(nota)

    suspend fun updateNota(nota: Nota) = notaDao.updateNota(nota)

    suspend fun deleteNota(nota: Nota) = notaDao.deleteNota(nota)
}