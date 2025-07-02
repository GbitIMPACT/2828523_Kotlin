package com.example.aviacionapp.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.aviacionapp.R
import com.example.aviacionapp.databinding.ActivityRegisterBinding
import com.example.aviacionapp.presentation.student.StudentMainActivity
import com.example.aviacionapp.utils.Result

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Configurar dropdown de tipo de identificación
        val tiposIdentificacion = arrayOf("CC", "TI", "Extranjero")
        val adapter = ArrayAdapter(this, R.layout.dropdown_menu_item, tiposIdentificacion)
        binding.actvTipoIdentificacion.setAdapter(adapter)

        binding.btnRegister.setOnClickListener {
            if (validateInputs()) {
                val nombre = binding.etNombre.text.toString().trim()
                val apellido = binding.etApellido.text.toString().trim()
                val edad = binding.etEdad.text.toString().toIntOrNull() ?: 0
                val tipoIdentificacion = binding.actvTipoIdentificacion.text.toString()
                val numeroIdentificacion = binding.etNumeroIdentificacion.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                viewModel.register(
                    nombre,
                    apellido,
                    edad,
                    tipoIdentificacion,
                    numeroIdentificacion,
                    email,
                    password
                )
            }
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (binding.etNombre.text.isNullOrBlank()) {
            binding.tilNombre.error = "El nombre es requerido"
            isValid = false
        } else {
            binding.tilNombre.error = null
        }

        if (binding.etApellido.text.isNullOrBlank()) {
            binding.tilApellido.error = "El apellido es requerido"
            isValid = false
        } else {
            binding.tilApellido.error = null
        }

        val edad = binding.etEdad.text.toString().toIntOrNull()
        if (edad == null || edad < 16 || edad > 100) {
            binding.tilEdad.error = "Edad inválida (16-100)"
            isValid = false
        } else {
            binding.tilEdad.error = null
        }

        if (binding.actvTipoIdentificacion.text.isNullOrBlank()) {
            binding.tilTipoIdentificacion.error = "Seleccione un tipo"
            isValid = false
        } else {
            binding.tilTipoIdentificacion.error = null
        }

        if (binding.etNumeroIdentificacion.text.isNullOrBlank()) {
            binding.tilNumeroIdentificacion.error = "El número es requerido"
            isValid = false
        } else {
            binding.tilNumeroIdentificacion.error = null
        }

        val email = binding.etEmail.text.toString()
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Correo inválido"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        val password = binding.etPassword.text.toString()
        if (password.length < 6) {
            binding.tilPassword.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        return isValid
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRegister.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true

                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, StudentMainActivity::class.java))
                    finishAffinity()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true

                    Toast.makeText(
                        this,
                        result.exception.message ?: "Error al registrar",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
