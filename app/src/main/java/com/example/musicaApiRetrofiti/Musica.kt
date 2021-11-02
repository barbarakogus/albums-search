package com.example.musicaApiRetrofiti

import com.google.gson.annotations.SerializedName

data class Musica (
    @SerializedName("name") val trackName: String,
    @SerializedName("text") val lyrics: String)


