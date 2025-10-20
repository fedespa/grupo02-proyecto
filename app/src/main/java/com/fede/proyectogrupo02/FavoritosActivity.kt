package com.fede.proyectogrupo02

import Ciudad
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.fede.proyectogrupo02.configurations.RetrofitClient
import com.fede.proyectogrupo02.dto.WeatherResponse
import com.fede.proyectogrupo02.restapi.WeatherApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class FavoritosActivity : AppCompatActivity() {

    lateinit var rvFavoritos: RecyclerView
    lateinit var ciudadesFavoritasAdapter: CiudadAdapter
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
                ciudadesFavoritasAdapter = CiudadAdapter(mutableListOf(), this@FavoritosActivity, true)
                rvFavoritos.adapter = ciudadesFavoritasAdapter

                cargarFavoritos(listaFavoritos)
            }


        }

    }

    private fun cargarFavoritos(listaFavoritos: List<CiudadFavorita>) {
        val apiWeather = RetrofitClient.retrofitWeather.create(WeatherApi::class.java)

        listaFavoritos.forEach { ciudadFav ->
            val call = apiWeather.getWeather(ciudadFav.lat, ciudadFav.lon)

            call.enqueue(object : retrofit2.Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    val weather = response.body()
                    if (weather != null) {
                        val temp = weather.current_weather.temperature
                        val wind = weather.current_weather.windspeed

                        val ciudad = Ciudad(
                            city = ciudadFav.name,
                            temperature = "${temp}°C",
                            weatherDescription = "Viento: ${wind} km/h",
                            ciudadFav.lat,
                            ciudadFav.lon
                        )

                        runOnUiThread {
                            ciudadesFavoritasAdapter.addCiudad(ciudad)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e("API_ERROR", "Error al cargar clima de ${ciudadFav.name}: ${t.message}")
                }
            })
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