package com.fede.proyectogrupo02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CiudadFavoritaAdapter(
    private var lista: List<CiudadFavorita>
): RecyclerView.Adapter<CiudadFavoritaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvTemperatura: TextView = itemView.findViewById(R.id.tvTemperatura)
        val tvClima: TextView = itemView.findViewById(R.id.tvClima)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ciudad_favorita, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ciudad = lista.get(position)
        holder.tvNombre.text = ciudad.nombre
        holder.tvTemperatura.text = ciudad.temperatura
        holder.tvClima.text = ciudad.clima
    }

    override fun getItemCount(): Int = lista.size

}