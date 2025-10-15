package com.fede.proyectogrupo02

import Ciudad
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CiudadAdapter (
    var ciudades: MutableList<Ciudad>,
    var context: Context
): RecyclerView.Adapter<CiudadAdapter.ElementoViewHolder>() {

    class ElementoViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val city: TextView = itemView.findViewById(R.id.tvCity)
        val weatherDescription: TextView = itemView.findViewById(R.id.tvWeatherDescription)
        val temperature: TextView = itemView.findViewById(R.id.tvTemperature)
    }

    override fun getItemCount() = ciudades.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ElementoViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ciudad, viewGroup, false)

        return ElementoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ElementoViewHolder, position: Int) {
        val item = ciudades.get(position)
        holder.city.text = item.city
        holder.weatherDescription.text = item.weatherDescription
        holder.temperature.text = item.temperature

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetalleElementoActivity::class.java)
            intent.putExtra("city", item.city)
            intent.putExtra("temperature", item.temperature)
            intent.putExtra("description", item.weatherDescription)
            intent.putExtra("lat", item.lat)
            intent.putExtra("lon", item.lon)
            context.startActivity(intent)
        }
    }

    fun addCiudad(ciudad: Ciudad) {
        ciudades.add(ciudad)
        notifyItemInserted(ciudades.size - 1)
    }

}