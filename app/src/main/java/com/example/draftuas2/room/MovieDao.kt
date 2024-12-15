package com.example.draftuas2.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): LiveData<List<DataFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movie: DataFavorite)

    @Delete
    suspend fun removeFavorite(movie: DataFavorite)
}

