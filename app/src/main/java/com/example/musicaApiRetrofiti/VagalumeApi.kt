package com.example.musicaApiRetrofiti

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VagalumeApi {

    @GET("search.php?apikey=9f69d64dc07d12f73d86e239ca6d72b3")
    fun buscarLetraMusica(
        @Query("art") art: String,
        @Query("mus") mus: String
    ) : Call<ResultadoLyrics>
}

