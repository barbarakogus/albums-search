package com.example.musicaApiRetrofiti

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val model = MainModel()

    val albums : MutableLiveData<ResultadoAlbums> = MutableLiveData()

    fun buscarAlbuns() {
        model.buscarAlbumApi("u2", onSuccess = {
            albums.postValue(it)
        },
        onFailure = {

        })
    }
}