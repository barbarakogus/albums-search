package com.example.musicaApiRetrofiti

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelLyrics : ViewModel() {

    private val model = ModelLyrics()

    val lyrics: MutableLiveData<ResultadoLyrics> = MutableLiveData()
    val barraProgresso: MutableLiveData<Boolean> = MutableLiveData()

    fun buscarLyrics(nomeCantor : String, nomeMusica: String) {
        barraProgresso.postValue(true)
        model.buscarLyricsApi(
            nomeCantor,
            nomeMusica,
            onSuccess = {
                lyrics.postValue(it)
                barraProgresso.postValue(false)
            },
            onFailure = {

            }
        )
    }
}