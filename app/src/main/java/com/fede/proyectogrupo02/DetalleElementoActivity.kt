package com.fede.proyectogrupo02

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class DetalleElementoActivity : AppCompatActivity() {

    lateinit var tvCity: TextView
    lateinit var tvTemp: TextView
    lateinit var tvDesc: TextView
    lateinit var btnVolver: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_elemento)

        tvCity = findViewById(R.id.tvCiudadDetalle)
        tvTemp = findViewById(R.id.tvTempDetalle)
        tvDesc = findViewById(R.id.tvDescDetalle)
        btnVolver = findViewById(R.id.btnVolver)

        tvCity.text = intent.getStringExtra("city") ?: ""
        tvTemp.text = intent.getStringExtra("temperature") ?: ""
        tvDesc.text = intent.getStringExtra("description") ?: ""


        btnVolver.setOnClickListener {
            finish()
        }
    }
}