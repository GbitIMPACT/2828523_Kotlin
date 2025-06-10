package com.sena.gestornotas.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas WHERE clienteId = :clienteId ORDER BY fecha DESC")
    fun getNotasByClienteId(clienteId: Int): Flow<List<Nota>>

    @Insert
    suspend fun insertNota(nota: Nota)

    @Update
    suspend fun updateNota(nota: Nota)

    @Delete
    suspend fun deleteNota(nota: Nota)
}