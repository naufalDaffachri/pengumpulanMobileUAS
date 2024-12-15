package com.example.draftuas2

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftuas2.databinding.FragmentHomeBinding
import com.example.draftuas2.model.Data
import com.example.draftuas2.network.ApiClient
import com.example.draftuas2.room.DataFavorite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        // Inisialisasi Adapter secara global
        movieAdapter = MovieAdapter(
            listMovie = listOf(),
            onClickMovie = { movie ->
                // Pastikan movie tidak null
                if (movie != null) {
                    movieViewModel.addToFavorites(movie)
                    Toast.makeText(requireContext(), "${movie.title} added to favorites", Toast.LENGTH_SHORT).show()
                }
            },
            isFavorite = false
        )

        with(binding.rvDisaster) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        // Panggil fetchMovies dengan adapter global
        fetchMovies()
    }

    private fun fetchMovies() {
        val apiService = ApiClient.getInstance()
        apiService.getAllUsers().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val movieData = response.body()?.filter {
                        it.title.isNotBlank() && it.genre.isNotBlank() && it.duration.isNotBlank()
                    }?.map { data ->
                        // Konversi Data ke DataFavorite
                        DataFavorite(
                            id = data.id.toString(),
                            title = data.title,
                            genre = data.genre,
                            duration = data.duration,
                            schedule = data.schedule,
                            cinema = data.cinema
                        )
                    } ?: emptyList()

                    // Update data ke adapter
                    movieAdapter.updateData(movieData)
                } else {
                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

