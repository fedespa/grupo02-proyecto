package com.fede.proyectogrupo02

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AuthActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Cargar Login por defecto solo la primera vez
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedor_auth, LoginFragmento())
                .commit()
        }
    }

    fun irARegistro() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor_auth, RegistroFragmento())
            .addToBackStack(null)
            .commit()
    }

    fun irALogin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor_auth, LoginFragmento())
            .addToBackStack(null)
            .commit()
    }

}