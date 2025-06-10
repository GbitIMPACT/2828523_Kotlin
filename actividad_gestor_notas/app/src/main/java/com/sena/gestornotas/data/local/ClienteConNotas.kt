package com.sena.gestornotas.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class ClienteConNotas(
    @Embedded val cliente: Cliente,
    @Relation(
        parentColumn = "id",
        entityColumn = "clienteId"
    )
    val notas: List<Nota>
)