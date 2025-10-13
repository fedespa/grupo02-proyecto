package com.fede.proyectogrupo02

import Ciudad
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class ListaActivity : AppCompatActivity() {

    lateinit var rvCiudades: RecyclerView
    lateinit var ciudadesAdapter: CiudadAdapter
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvCiudades = findViewById(R.id.rvCiudades)
        ciudadesAdapter = CiudadAdapter(getElementos(), this)
        rvCiudades.adapter = ciudadesAdapter

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            logout()
        } else if (item.itemId == R.id.action_favorites) {
            val intent = Intent(this, FavoritosActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logout(){
        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        preferencias.edit().remove(resources.getString(R.string.email)).apply()
        preferencias.edit().remove(resources.getString(R.string.password)).apply()

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getElementos(): MutableList<Ciudad> {
        var ciudads: MutableList<Ciudad> = ArrayList();

        ciudads.add(Ciudad("Buenos Aires", "25°C", "Soleado"))
        ciudads.add(Ciudad("Madrid", "18°C", "Parcialmente nublado"))
        ciudads.add(Ciudad("Nueva York", "12°C", "Lluvia ligera"))
        ciudads.add(Ciudad("Tokio", "30°C", "Húmedo y caluroso"))
        ciudads.add(Ciudad("París", "20°C", "Cielo despejado"))

        return ciudads;
    }
}