package com.fede.proyectogrupo02

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {


    // Insertar un usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(usuario: Usuario)

    // Validar login (devuelve usuario si existe con ese email y contraseña)
    @Query("SELECT * FROM usuario_entity WHERE email = :email AND password = :password LIMIT 1")
    fun login(email: String, password: String): Usuario?

    @Query("SELECT * FROM usuario_entity WHERE email = :email LIMIT 1")
    fun getByEmail(email: String): Usuario?

}