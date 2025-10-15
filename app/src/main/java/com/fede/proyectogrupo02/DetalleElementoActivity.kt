package com.fede.proyectogrupo02

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class DetalleElementoActivity : AppCompatActivity() {

    lateinit var tvCity: TextView
    lateinit var tvTemp: TextView
    lateinit var tvDesc: TextView
    lateinit var btnVolver: Button
    lateinit var btnAgregarFavorito: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ciudad_elemento)

        tvCity = findViewById(R.id.tvCiudadDetalle)
        tvTemp = findViewById(R.id.tvTempDetalle)
        tvDesc = findViewById(R.id.tvDescDetalle)
        btnVolver = findViewById(R.id.btnVolver)
        btnAgregarFavorito = findViewById(R.id.btnAgregarFavorito)

        val nombreCiudad = intent.getStringExtra("city") ?: ""
        val temperatura = intent.getStringExtra("temperature") ?: ""
        val descripcion = intent.getStringExtra("description") ?: ""
        val lat = intent.getDoubleExtra("lat", 0.0)
        val lon = intent.getDoubleExtra("lon", 0.0)

        tvCity.text = nombreCiudad
        tvTemp.text = temperatura
        tvDesc.text = descripcion

        val db = AppDatabase.getDatabase(this)

        btnAgregarFavorito.setOnClickListener {
            val ciudad = CiudadFavorita(
                name = nombreCiudad,
                lat = lat,
                lon = lon
            )

            lifecycleScope.launch {
                db.ciudadFavoritaDao().insert(ciudad)
                runOnUiThread {
                    Toast.makeText(this@DetalleElementoActivity, "Agregado a favoritos ✅", Toast.LENGTH_SHORT).show()
                }
            }
        }


        btnVolver.setOnClickListener {
            finish()
        }
    }
}