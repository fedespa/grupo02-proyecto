package com.fede.proyectogrupo02

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CiudadFavoritaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ciudad: CiudadFavorita)

    @Delete
    fun delete(ciudad: CiudadFavorita)

    @Query("SELECT * FROM ciudad_favorita_entity")
    fun getAll(): List<CiudadFavorita>

}