package com.example.draftuas2.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("schedule")
    val schedule: String,
    @SerializedName("cinema")
    val cinema: String,
)
