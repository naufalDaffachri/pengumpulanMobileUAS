package com.example.draftuas2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.draftuas2.model.Data

class MovieAdapterAdmin(
    private var listMovie: List<Data>,
    private val onClickMovie: (Data) -> Unit, // Callback untuk item click
    private val onEditMovie: (Data) -> Unit, // Callback untuk edit
    private val onDeleteMovie: (Data) -> Unit // Callback untuk delete
) : RecyclerView.Adapter<MovieAdapterAdmin.ItemMovieViewHolder>() {

    inner class ItemMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data) {
            with(itemView) {
                val txtMovieTitle = findViewById<TextView>(R.id.txt_movie_title)
                val txtMovieGenre = findViewById<TextView>(R.id.txt_movie_genre)
                val txtMovieDuration = findViewById<TextView>(R.id.txt_movie_duration)
                val txtMovieSchedule = findViewById<TextView>(R.id.txt_movie_schedule)
                val txtCinema = findViewById<TextView>(R.id.txt_cinema)
                val btnEdit = findViewById<ImageButton>(R.id.btn_edit)
                val btnDelete = findViewById<ImageButton>(R.id.btn_delete)

                // Bind data
                txtMovieTitle.text = data.title
                txtMovieGenre.text = data.genre
                txtMovieDuration.text = data.duration
                txtMovieSchedule.text = data.schedule
                txtCinema.text = data.cinema

                // Handle edit button click
                btnEdit.setOnClickListener {
                    onEditMovie(data) // Panggil callback untuk edit movie
                }

                // Handle delete button click
                btnDelete.setOnClickListener {
                    onDeleteMovie(data) // Panggil callback untuk delete movie
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie_admin, parent, false) // Layout untuk item movie admin
        return ItemMovieViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ItemMovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    // Fungsi untuk memperbarui data
    fun updateData(newList: List<Data>) {
        listMovie = newList
        notifyDataSetChanged()
    }
}


