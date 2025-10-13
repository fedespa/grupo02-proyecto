package com.fede.proyectogrupo02

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FavoritosActivity : AppCompatActivity() {

    lateinit var rvFavoritos: RecyclerView
    lateinit var ciudadesFavoritasAdapter: CiudadFavoritaAdapter
    lateinit var toolbar: Toolbar
    private lateinit var tvEmptyMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = AppDatabase.getDatabase(this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)

        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)
        rvFavoritos = findViewById(R.id.rvFavoritos)

        lifecycleScope.launch {
            val listaFavoritos = db.ciudadFavoritaDao().getAll()

            if (listaFavoritos.isEmpty()) {
                rvFavoritos.visibility = View.GONE
                tvEmptyMessage.visibility = View.VISIBLE
            } else {
                rvFavoritos.visibility = View.VISIBLE
                tvEmptyMessage.visibility = View.GONE
                ciudadesFavoritasAdapter = CiudadFavoritaAdapter(listaFavoritos)
                rvFavoritos.adapter = ciudadesFavoritasAdapter
            }


        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            logout()
        } else if (item.itemId == R.id.listado_ciudades) {
            val intent = Intent(this, ListaActivity::class.java)
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

}