package com.example.aviacionapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aviacionapp.presentation.auth.login.LoginActivity
import com.example.aviacionapp.presentation.professor.ProfessorMainActivity
import com.example.aviacionapp.presentation.student.StudentMainActivity
import com.example.aviacionapp.utils.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferencesManager = PreferencesManager(this)

        lifecycleScope.launch {
            val isLoggedIn = preferencesManager.isLoggedIn.first()
            val userType = preferencesManager.userType.first()

            val intent = when {
                !isLoggedIn -> Intent(this@MainActivity, LoginActivity::class.java)
                userType == "professor" -> Intent(this@MainActivity, ProfessorMainActivity::class.java)
                userType == "student" -> Intent(this@MainActivity, StudentMainActivity::class.java)
                else -> Intent(this@MainActivity, LoginActivity::class.java)
            }

            startActivity(intent)
            finish()
        }
    }
}