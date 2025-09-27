package com.fede.proyectogrupo02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button
    lateinit var textLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegistrar)
        textLogin = findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener {
            if (etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty() ||
                etConfirmPassword.text.toString().isEmpty() || etName.text.toString().isEmpty()) {
                Toast.makeText(this, "No ingresó todos los datos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
                Toast.makeText(this, "Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombre = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val db = AppDatabase.getDatabase(this)

            lifecycleScope.launch(Dispatchers.IO) {
                // Verificar si ya existe
                val existente = db.usuarioDao().getByEmail(email)

                withContext(Dispatchers.Main) {
                    if (existente != null) {
                        Toast.makeText(this@RegistroActivity, "El email ya está registrado", Toast.LENGTH_SHORT).show()
                    } else {
                        val nuevoUsuario = Usuario(name = nombre, email = email, password = password)
                        db.usuarioDao().insert(nuevoUsuario)

                        Toast.makeText(this@RegistroActivity, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegistroActivity, ListaActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }



        }

        textLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}