package com.fede.proyectogrupo02

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragmento: Fragment() {

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var textRegister: TextView
    lateinit var cbRemember: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragmento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        textRegister = view.findViewById(R.id.tvRegistro)
        cbRemember = view.findViewById(R.id.cbRemember)

        cbRemember.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(requireContext(), "Recordar usuario activado ✅", Toast.LENGTH_SHORT).show()
            }
        }

        var preferencias = requireContext().getSharedPreferences(
            getString(R.string.sp_credenciales),
            MODE_PRIVATE
        )
        var emailGuardado = preferencias.getString(resources.getString(R.string.email), "")
        var passwordGuardado = preferencias.getString(resources.getString(R.string.password), "")

        if (emailGuardado!!.isNotEmpty() && passwordGuardado!!.isNotEmpty())
            iniciarActividad()

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "No ingresó todos los datos!", Toast.LENGTH_SHORT).show()
            } else {

                val db = AppDatabase.getDatabase(requireContext())

                lifecycleScope.launch {
                    val user = db.usuarioDao().login(email, password)

                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            if (cbRemember.isChecked){
                                var preferencias = requireContext().getSharedPreferences(
                                    getString(R.string.sp_credenciales),
                                    MODE_PRIVATE
                                )
                                preferencias.edit().putString(resources.getString(R.string.email), email).apply()
                                preferencias.edit().putString(resources.getString(R.string.password), password).apply()
                            }
                            iniciarActividad()
                        } else {
                            Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        textRegister.setOnClickListener {
            (activity as? AuthActivity)?.irARegistro()
        }
    }

    private fun iniciarActividad(){
        Toast.makeText(requireContext(), "Inició sesión!", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), ListaActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}