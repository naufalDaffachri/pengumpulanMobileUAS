package com.example.draftuas2

import android.app.Activity
import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.draftuas2.model.Data
import com.example.draftuas2.network.ApiClient
import org.jetbrains.annotations.Async.Schedule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMovieActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etGenre: EditText
    private lateinit var etDuration: EditText
    private lateinit var etSchedule: EditText
    private lateinit var etCinema: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        // Bind views
        etTitle = findViewById(R.id.et_title)
        etGenre = findViewById(R.id.et_genre)
        etDuration = findViewById(R.id.et_duration)
        etSchedule = findViewById(R.id.et_schedule)
        etCinema = findViewById(R.id.et_cinema)
        btnSave = findViewById(R.id.btn_save)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val genre = etGenre.text.toString()
            val duration = etDuration.text.toString()
            val schedule = etSchedule.text.toString()
            val cinema = etCinema.text.toString()

            if (title.isNotEmpty() && genre.isNotEmpty() && duration.isNotEmpty() && schedule.isNotEmpty() && cinema.isNotEmpty()) {
                // Simpan data ke database
                saveMovieData(title, genre, duration, schedule, cinema)
            } else {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveMovieData(title: String, genre: String, duration: String, schedule: String, cinema: String) {
        val apiService = ApiClient.getInstance()

        val movie = Data(
            title = title,
            genre = genre,
            duration = duration,
            schedule = schedule,
            cinema = cinema
        )

        apiService.addMovie(movie).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddMovieActivity, "Film berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    startActivity(Intent(this@AddMovieActivity, HomeFragmentAdmin::class.java))// Mengembalikan hasil ke HomeFragmentAdmin
                    finish()
                } else {
                    Toast.makeText(this@AddMovieActivity, "Gagal menambah film", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(this@AddMovieActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
