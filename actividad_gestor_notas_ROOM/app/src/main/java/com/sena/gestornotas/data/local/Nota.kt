package com.sena.gestornotas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "notas",
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["id"],
            childColumns = ["clienteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Nota(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val contenido: String,
    val fecha: String,
    val clienteId: Int
)