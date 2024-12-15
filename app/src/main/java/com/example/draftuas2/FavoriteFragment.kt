package com.example.draftuas2

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftuas2.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup ViewModel
        movieViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(MovieViewModel::class.java)

        // Setup RecyclerView
        var favoriteAdapter = MovieAdapter(emptyList(), { movie ->
            Toast.makeText(requireContext(), "Selected: ${movie.title}", Toast.LENGTH_SHORT).show()
        }, isFavorite = true)

        movieViewModel.favoriteMovies.observe(viewLifecycleOwner) { favoriteMovies ->
            if (favoriteMovies.isEmpty()) {
                Toast.makeText(requireContext(), "No favorite movies yet", Toast.LENGTH_SHORT).show()
            }
            favoriteAdapter.updateData(favoriteMovies) // Update data ke adapter
        }


        favoriteAdapter = MovieAdapter(
            listMovie = emptyList(), // List kosong saat pertama kali, nanti akan di-update dengan data favorit
            onClickMovie = { movie ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Remove from Favorites")
                    .setMessage("Are you sure you want to remove ${movie.title} from your favorites?")
                    .setPositiveButton("Yes") { _, _ ->
                        movieViewModel.removeFromFavorites(movie)
                        Toast.makeText(requireContext(), "${movie.title} removed from favorites", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                    .show()
            },
            isFavorite = true
        )



        with(binding.rvFavorites) {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


