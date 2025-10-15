package com.fede.proyectogrupo02

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ciudad_favorita_entity")
data class CiudadFavorita (
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "lat") var lat: Double,
    @ColumnInfo(name = "lon") var lon: Double,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}