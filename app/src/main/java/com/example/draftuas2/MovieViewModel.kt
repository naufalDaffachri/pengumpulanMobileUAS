package com.example.draftuas2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.draftuas2.room.AppDatabase
import com.example.draftuas2.room.DataFavorite

import com.example.draftuas2.room.MovieDao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao: MovieDao = AppDatabase.getDatabase(application).movieDao()

    val favoriteMovies: LiveData<List<DataFavorite>> = movieDao.getAllFavorites()

    fun addToFavorites(movie: DataFavorite) {
        viewModelScope.launch {
            movieDao.addFavorite(movie)
        }
    }

    fun removeFromFavorites(movie: DataFavorite) {
        viewModelScope.launch {
            movieDao.removeFavorite(movie)
        }
    }
}