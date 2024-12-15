package com.example.draftuas2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.draftuas2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)

        with(binding) {
            btnLogin.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
                } else {
                    if (isValidUsernamePassword(username, password)) {
                        prefManager.setLoggedIn(true)
                        checkLoginStatus(username)
                    } else {
                        Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            txtRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun isValidUsernamePassword(username: String, password: String): Boolean {
        return (username == "admin" && password == "12345") || (username == prefManager.getUsername() && password == prefManager.getPassword())
    }

    private fun checkLoginStatus(username: String) {
        val isLoggedIn = prefManager.isLoggedIn()
        if (isLoggedIn) {
            if (username == "admin") {
                startActivity(Intent(this@LoginActivity, MainActivityAdmin::class.java))
            } else {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
            finish()
        } else {
            Toast.makeText(this@LoginActivity, "Login gagal", Toast.LENGTH_SHORT).show()
        }
    }
}
