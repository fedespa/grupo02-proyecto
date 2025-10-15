package com.fede.proyectogrupo02

import Ciudad
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fede.proyectogrupo02.configurations.RetrofitClient
import com.fede.proyectogrupo02.dto.GeocodingResponse
import com.fede.proyectogrupo02.dto.WeatherResponse
import com.fede.proyectogrupo02.restapi.GeocodingApi
import com.fede.proyectogrupo02.restapi.WeatherApi
import retrofit2.Call
import retrofit2.Response

class ListaActivity : AppCompatActivity() {

    lateinit var rvCiudades: RecyclerView
    lateinit var ciudadesAdapter: CiudadAdapter
    lateinit var toolbar: Toolbar
    lateinit var progressBar: ProgressBar
    var totalCiudades = 0
    var ciudadesCargadas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ciudadesIniciales = listOf("Buenos Aires", "Madrid", "Tokio", "París", "Nueva York")

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)

        progressBar = findViewById(R.id.progressBar)
        rvCiudades = findViewById(R.id.rvCiudades)
        ciudadesAdapter = CiudadAdapter(mutableListOf(), this)
        rvCiudades.adapter = ciudadesAdapter

        progressBar.visibility = View.VISIBLE
        rvCiudades.visibility = View.GONE

        cargarCiudades(ciudadesIniciales)
    }

    private fun cargarCiudades(ciudades: List<String>) {
        val api = RetrofitClient.retrofitGeo.create(GeocodingApi::class.java)
        val apiWeather = RetrofitClient.retrofitWeather.create(WeatherApi::class.java)

        totalCiudades = ciudades.size
        ciudadesCargadas = 0

        ciudades.forEach { nombreCiudad ->
            val call = api.searchCity(nombreCiudad)
            call.enqueue(object: retrofit2.Callback<GeocodingResponse> {
                override fun onResponse(
                    call: Call<GeocodingResponse>,
                    response: Response<GeocodingResponse>
                ){
                    val geoBody = response.body()
                    if (geoBody != null && !geoBody.results.isNullOrEmpty()){
                        val ciudadGeo = geoBody.results[0]

                        val lat = ciudadGeo.latitude
                        val lon = ciudadGeo.longitude

                        if (lat != null && lon != null && ciudadGeo.name != null) {
                            val callWeather = apiWeather.getWeather(
                                lat,
                                lon
                            )

                            callWeather.enqueue(object : retrofit2.Callback<WeatherResponse> {
                                override fun onResponse(
                                    call: Call<WeatherResponse>,
                                    response: Response<WeatherResponse>
                                ) {
                                    val weather = response.body()
                                    if (weather != null) {
                                        val temp = weather.current_weather.temperature
                                        val wind = weather.current_weather.windspeed

                                        val nuevaCiudad = Ciudad(
                                            ciudadGeo.name,
                                            "${temp}°C",
                                            "Viento: ${wind} km/h",
                                            lat,
                                            lon
                                        )

                                        runOnUiThread {
                                            ciudadesAdapter.addCiudad(nuevaCiudad)
                                            ciudadesCargadas++

                                            if (ciudadesCargadas == totalCiudades) {
                                                progressBar.visibility = View.GONE
                                                rvCiudades.visibility = View.VISIBLE
                                            }
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                                    Log.e("API_ERROR", "Error clima ${nombreCiudad}: ${t.message}")
                                    onCiudadCargada()
                                }
                            })
                        }  else {
                            Log.w("API_WARNING", "Ciudad sin coordenadas válidas: ${ciudadGeo.name}")
                            onCiudadCargada()
                        }
                    } else {
                        onCiudadCargada()
                    }
                }

                override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                    Log.e("API_ERROR", "Error geocoding ${nombreCiudad}: ${t.message}")
                    onCiudadCargada()
                }
            })
        }
    }

    private fun onCiudadCargada() {
        runOnUiThread {
            ciudadesCargadas++
            if (ciudadesCargadas == totalCiudades) {
                progressBar.visibility = View.GONE
                rvCiudades.visibility = View.VISIBLE
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
}