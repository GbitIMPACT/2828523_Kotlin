package com.example.aviacionapp.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.aviacionapp.R
import com.example.aviacionapp.databinding.ActivityLoginBinding
import com.example.aviacionapp.databinding.DialogProfessorLoginBinding
import com.example.aviacionapp.presentation.auth.register.RegisterActivity
import com.example.aviacionapp.presentation.student.StudentMainActivity
import com.example.aviacionapp.presentation.professor.ProfessorMainActivity
import com.example.aviacionapp.utils.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInputs(email, password)) {
                viewModel.loginStudent(email, password)
            }
        }

        binding.btnLoginProfessor.setOnClickListener {
            showProfessorLoginDialog()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.tilEmail.error = "El correo es requerido"
            return false
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "La contraseña es requerida"
            return false
        } else {
            binding.tilPassword.error = null
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Correo inválido"
            return false
        }

        return true
    }

    private fun showProfessorLoginDialog() {
        val dialogBinding = DialogProfessorLoginBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnLogin.setOnClickListener {
            val codigo = dialogBinding.etCodigoInstructor.text.toString().trim()
            val password = dialogBinding.etPassword.text.toString().trim()

            if (codigo.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginProfessor(codigo, password)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false
                    binding.btnLoginProfessor.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    binding.btnLoginProfessor.isEnabled = true

                    when (result.data) {
                        "student" -> {
                            startActivity(Intent(this, StudentMainActivity::class.java))
                            finish()
                        }
                        "professor" -> {
                            startActivity(Intent(this, ProfessorMainActivity::class.java))
                            finish()
                        }
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    binding.btnLoginProfessor.isEnabled = true

                    Toast.makeText(
                        this,
                        result.exception.message ?: "Error al iniciar sesión",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}