package com.example.draftuas2.room
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_movies")
data class DataFavorite(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("schedule")
    val schedule: String,
    @SerializedName("cinema")
    val cinema: String
)



