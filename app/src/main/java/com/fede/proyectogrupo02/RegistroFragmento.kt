package com.fede.proyectogrupo02

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroFragmento: Fragment() {


    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button
    lateinit var textLogin: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registro_fragmento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        btnRegister = view.findViewById(R.id.btnRegistrar)
        textLogin = view.findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener {
            if (etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty() ||
                etConfirmPassword.text.toString().isEmpty() || etName.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "No ingresó todos los datos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombre = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val db = AppDatabase.getDatabase(requireContext())

            lifecycleScope.launch(Dispatchers.IO) {
                val existente = db.usuarioDao().getByEmail(email)

                withContext(Dispatchers.Main) {
                    if (existente != null) {
                        Toast.makeText(requireContext(), "El email ya está registrado", Toast.LENGTH_SHORT).show()
                    } else {
                        val nuevoUsuario = Usuario(name = nombre, email = email, password = password)
                        db.usuarioDao().insert(nuevoUsuario)

                        Toast.makeText(requireContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), ListaActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
        }

        textLogin.setOnClickListener {
            (activity as? AuthActivity)?.irALogin()
        }

    }



}