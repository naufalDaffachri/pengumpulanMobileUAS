package com.example.draftuas2

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.draftuas2.databinding.ActivityEditMovieBinding
import com.example.draftuas2.model.Data
import com.example.draftuas2.network.ApiClient
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// EditMovieActivity.kt
class EditMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deserialize data from Intent
        val movieJson = intent.getStringExtra("movie")
        val movie = Gson().fromJson(movieJson, Data::class.java)

        with(binding) {
            editTitle.setText(movie.title)
            editGenre.setText(movie.genre)
            editDuration.setText(movie.duration)
            editSchedule.setText(movie.schedule)
            editCinema.setText(movie.cinema)

            btnSaveEdit.setOnClickListener {
                val updatedMovie = Data(
                    id = movie.id,
                    title = editTitle.text.toString(),
                    genre = editGenre.text.toString(),
                    duration = editDuration.text.toString(),
                    schedule = editSchedule.text.toString(),
                    cinema = editCinema.text.toString()
                )
                updateMovie(updatedMovie)
            }
        }
    }

    private fun updateMovie(movie: Data) {
        val requestBody = Gson().toJson(movie).toRequestBody("application/json".toMediaType())
        val client = ApiClient.getInstance()
        client.updateMovie(movie.id!!, requestBody).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditMovieActivity, "Berhasil mengubah data", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@EditMovieActivity, "Gagal mengubah data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(this@EditMovieActivity, "Koneksi error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}