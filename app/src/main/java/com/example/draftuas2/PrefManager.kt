package com.example.draftuas2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.draftuas2.databinding.ActivityDashboardProfileBinding

class PrefManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS_FILENAME = "AuthAppPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val PREF_NAME = "bookmark_prefs"
        private const val BOOKMARK_KEY = "bookmark_status"

        @Volatile
        private var instance: PrefManager? = null

        fun getInstance(context: Context): PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun savePassword(password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "") ?: ""
    }

    fun getPassword(): String {
        return sharedPreferences.getString(KEY_PASSWORD, "") ?: ""
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    // Fungsi untuk menyimpan status bookmark berdasarkan ID film
    fun saveBookmarkStatus(context: Context, movieId: String, isBookmarked: Boolean) {
        val sharedPreferences = context.getSharedPreferences("bookmark_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(movieId, isBookmarked).apply()
    }

    // Fungsi untuk mengambil status bookmark berdasarkan ID film
    fun getBookmarkStatus(context: Context, movieId: String): Boolean {
        val sharedPreferences = context.getSharedPreferences("bookmark_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(movieId, false) // Default: false
    }


    fun setupBookmarkButton(button: ImageButton, context: Context, movieId: String) {
        // Set initial state based on saved status
        val isBookmarked = getBookmarkStatus(context, movieId)
        updateButtonAppearance(button, isBookmarked)

        // Handle button click
        button.setOnClickListener {
            val newStatus = !getBookmarkStatus(context, movieId)
            saveBookmarkStatus(context, movieId, newStatus)
            updateButtonAppearance(button, newStatus)
        }
    }


    fun updateButtonAppearance(button: ImageButton, isBookmarked: Boolean) {
        if (isBookmarked) {
            button.setImageResource(R.drawable.baseline_bookmark_24) // Bookmark penuh
        } else {
            button.setImageResource(R.drawable.baseline_bookmark_border_24) // Bookmark kosong
        }
    }
}
