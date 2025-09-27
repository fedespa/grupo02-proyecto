package com.fede.proyectogrupo02

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ciudad_favorita_entity")
data class CiudadFavorita (
    @ColumnInfo(name = "city") var nombre: String,
    @ColumnInfo(name = "temperature") var temperatura: String,
    @ColumnInfo(name = "weatherDescription") var clima: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}