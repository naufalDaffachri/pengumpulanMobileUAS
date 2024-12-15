package com.example.draftuas2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftuas2.databinding.FragmentHomeAdminBinding
import com.example.draftuas2.databinding.FragmentHomeBinding
import com.example.draftuas2.model.Data
import com.example.draftuas2.network.ApiClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentAdmin : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapterAdmin
    private lateinit var movieViewModel: MovieViewModel

    // Inisialisasi listMovie untuk menampung data yang diterima dari API
    private var movieList: List<Data> = emptyList()

    // Inisialisasi launcher untuk menangani hasil dari AddMovieActivity
    private val addMovieLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Memanggil fetchMovies() untuk memperbarui data setelah menambahkan film baru
            fetchMovies()
        }
    }

    private val editMovieLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            fetchMovies()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        // Inisialisasi movieAdapter setelah mendapatkan data
        movieAdapter = MovieAdapterAdmin(
            listMovie = movieList,
            onClickMovie = { movie ->
                // Handle item click
                Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
            },
            onEditMovie = { movie ->
                val intent = Intent(requireContext(), EditMovieActivity::class.java)
                intent.putExtra("movie", Gson().toJson(movie))
                editMovieLauncher.launch(intent)
            },
            onDeleteMovie = { movie ->
                movie.id?.let { id ->
                    deleteMovie(id)
                } ?: Toast.makeText(requireContext(), "ID film tidak valid", Toast.LENGTH_SHORT).show()
            }
        )

        // Atur RecyclerView
        with(binding.rvDisasterAdmin) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Setup tombol tambah data
        binding.btnAddData.setOnClickListener {
            val intent = Intent(requireContext(), AddMovieActivity::class.java)
            addMovieLauncher.launch(intent)
        }

        // Panggil fetchMovies untuk memuat data dari API
        fetchMovies()
    }

    private fun fetchMovies() {
        val apiService = ApiClient.getInstance()
        apiService.getAllUsers().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val movieData = response.body()?.filter {
                        it.title.isNotBlank() && it.genre.isNotBlank() && it.duration.isNotBlank()
                    } ?: emptyList()
                    movieList = movieData // Simpan data yang diterima ke dalam movieList
                    movieAdapter.updateData(movieData) // Update data ke adapter
                } else {
                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteMovie(id: String) {
        val apiService = ApiClient.getInstance()
        apiService.deleteMovie(id).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Film berhasil dihapus", Toast.LENGTH_SHORT).show()
                    fetchMovies() // Perbarui data setelah penghapusan
                } else {
                    Toast.makeText(requireContext(), "Gagal menghapus film", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.e("API_ERROR", "Error deleting movie: ${t.message}", t)
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

