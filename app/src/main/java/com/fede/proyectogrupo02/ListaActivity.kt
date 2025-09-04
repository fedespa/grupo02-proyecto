package com.fede.proyectogrupo02

import Elemento
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class ListaActivity : AppCompatActivity() {

    lateinit var rvElementos: RecyclerView
    lateinit var elementosAdapter: ElementoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvElementos = findViewById(R.id.rvElementos)
        elementosAdapter = ElementoAdapter(getElementos(), this)
        rvElementos.adapter = elementosAdapter
    }

    private fun getElementos(): MutableList<Elemento> {
        var elementos: MutableList<Elemento> = ArrayList();

        elementos.add(Elemento("Buenos Aires", "25°C", "Soleado"))
        elementos.add(Elemento("Madrid", "18°C", "Parcialmente nublado"))
        elementos.add(Elemento("Nueva York", "12°C", "Lluvia ligera"))
        elementos.add(Elemento("Tokio", "30°C", "Húmedo y caluroso"))
        elementos.add(Elemento("París", "20°C", "Cielo despejado"))

        return elementos;
    }
}