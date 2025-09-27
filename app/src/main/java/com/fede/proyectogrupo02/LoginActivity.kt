package com.fede.proyectogrupo02

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var textRegister: TextView
    lateinit var cbRemember: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        textRegister = findViewById(R.id.tvRegistro)
        cbRemember = findViewById(R.id.cbRemember)

        var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        var emailGuardado = preferencias.getString(resources.getString(R.string.email), "")
        var passwordGuardado = preferencias.getString(resources.getString(R.string.password), "")

        if (emailGuardado!!.isNotEmpty() && passwordGuardado!!.isNotEmpty())
            iniciarActividad()


        btnLogin.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "No ingresó todos los datos!", Toast.LENGTH_SHORT).show()
            } else {

                val db = AppDatabase.getDatabase(this)

                lifecycleScope.launch {
                    val user = db.usuarioDao().login(email, password)

                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            if (cbRemember.isChecked){
                                var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                                preferencias.edit().putString(resources.getString(R.string.email), email).apply()
                                preferencias.edit().putString(resources.getString(R.string.password), password).apply()
                            }
                            iniciarActividad()
                        } else {
                            Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        textRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun iniciarActividad(){
        Toast.makeText(this@LoginActivity, "Inició sesión!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@LoginActivity, ListaActivity::class.java)
        startActivity(intent)
        finish()
    }

}
