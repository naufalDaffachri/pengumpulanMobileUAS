package com.example.draftuas2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.draftuas2.databinding.ActivityDashboardProfileBinding

class DashboardProfile : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardProfileBinding
    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()
        with(binding) {
            txtUsername.text = prefManager.getUsername()
            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false)
                startActivity(Intent(this@DashboardProfile, LoginActivity::class.java))
                finish()
            }
            btnClear.setOnClickListener {
                prefManager.clear()
                startActivity(Intent(this@DashboardProfile, RegisterActivity::class.java))
                finish() }
        }
    }
    fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (!isLoggedIn) {
            startActivity(Intent(this@DashboardProfile, LoginActivity::class.java))
            finish() }
    }
}