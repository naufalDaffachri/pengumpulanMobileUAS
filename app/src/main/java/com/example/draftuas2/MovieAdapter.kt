package com.example.draftuas2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.draftuas2.databinding.ItemFavoriteMovieBinding
import com.example.draftuas2.databinding.ItemMovieBinding
import com.example.draftuas2.model.Data
import com.example.draftuas2.room.DataFavorite

typealias OnClickMovie = (DataFavorite) -> Unit

class MovieAdapter(
    private var listMovie: List<DataFavorite>,  // Ganti Data dengan DataFavorite
    private val onClickMovie: OnClickMovie,
    private val isFavorite: Boolean
) : RecyclerView.Adapter<MovieAdapter.ItemMovieViewHolder>() {
    inner class ItemMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: DataFavorite) {  // Ganti Data dengan DataFavorite
            with(itemView) {
                val txtMovieTitle = findViewById<TextView>(R.id.txt_movie_title)
                val txtMovieGenre = findViewById<TextView>(R.id.txt_movie_genre)
                val txtMovieDuration = findViewById<TextView>(R.id.txt_movie_duration)
                val txtMovieSchedule = findViewById<TextView>(R.id.txt_movie_schedule)
                val txtCinema = findViewById<TextView>(R.id.txt_cinema)
                val btnSave = findViewById<ImageButton?>(R.id.btn_save)

                // Bind data
                txtMovieTitle.text = data.title
                txtMovieGenre.text = data.genre
                txtMovieDuration.text = data.duration
                txtMovieSchedule.text = data.schedule
                txtCinema.text = data.cinema

                // Handle tombol Save
                btnSave?.setOnClickListener {
                    onClickMovie(data)  // Panggil callback untuk menyimpan
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = if (isFavorite) {
            inflater.inflate(R.layout.item_favorite_movie, parent, false) // Layout favorit
        } else {
            inflater.inflate(R.layout.item_movie, parent, false) // Layout home
        }
        return ItemMovieViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ItemMovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    fun updateData(newList: List<DataFavorite>) {
        listMovie = newList
        notifyDataSetChanged()
    }
}

