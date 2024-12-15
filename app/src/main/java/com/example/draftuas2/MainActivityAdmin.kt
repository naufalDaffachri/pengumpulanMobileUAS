package com.example.draftuas2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.draftuas2.databinding.ActivityMainAdminBinding
import com.example.draftuas2.databinding.ActivityMainBinding

class MainActivityAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityMainAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menggunakan View Binding
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Navigation
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}