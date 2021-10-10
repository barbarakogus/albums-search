package com.example.musicaApiRetrofiti

//1 - Iniciando código kt - criar dataClass que vao mapear o JSON
data class Album(
    val artistName: String,
    val collectionName: String,
    val artworkUrl100: String,
    val trackCount: Int,
    val trackName: String,
    val trackNumber: Int
)

//representacao dos campos da API em data class