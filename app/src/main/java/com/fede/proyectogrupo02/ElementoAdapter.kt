package com.fede.proyectogrupo02

import Elemento
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ElementoAdapter (
    var elementos: MutableList<Elemento>,
    var context: Context
): RecyclerView.Adapter<ElementoAdapter.ElementoViewHolder>() {

    class ElementoViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val city: TextView = itemView.findViewById(R.id.tvCity)
        val weatherDescription: TextView = itemView.findViewById(R.id.tvWeatherDescription)
        val temperature: TextView = itemView.findViewById(R.id.tvTemperature)
    }

    override fun getItemCount() = elementos.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ElementoViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_elemento, viewGroup, false)

        return ElementoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ElementoViewHolder, position: Int) {
        val item = elementos.get(position)
        holder.city.text = item.city
        holder.weatherDescription.text = item.weatherDescription
        holder.temperature.text = item.temperature

        holder.itemView.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, item.city, Toast.LENGTH_SHORT).show() }
        )
    }

}