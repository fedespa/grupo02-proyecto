package com.fede.proyectogrupo02

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario_entity")
data class Usuario (
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "password") var password: String
) {
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}