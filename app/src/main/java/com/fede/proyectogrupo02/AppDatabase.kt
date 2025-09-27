package com.fede.proyectogrupo02

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class, CiudadFavorita::class], version = 2)

abstract class AppDatabase: RoomDatabase()  {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun ciudadFavoritaDao(): CiudadFavoritaDao

    companion object{
        private var INSTANCIA: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if(INSTANCIA == null){
                synchronized(this){
                    INSTANCIA = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "clima_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCIA!!
        }
    }

}