package com.example.musicaApiRetrofiti

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelFaixas : ViewModel() {

    private val modelFaixas = ModelFaixas()

    val albums: MutableLiveData<ResultadoAlbums> = MutableLiveData() //instanciando um objeto MutableLiveData, que é do tipo MitableLiveData, onde os dados sao do tipo ResultadoAlbum
    val mensagemErro: MutableLiveData<String> = MutableLiveData()
    val barraProgresso: MutableLiveData<Boolean> = MutableLiveData()

    fun buscarFaixas(term : String) {
        barraProgresso.postValue(true)
        modelFaixas.buscarFaixasApi(term,
            onSuccess = {
                albums.postValue(it)
                Log.d("api", it.toString())
                barraProgresso.postValue(false)
                Log.d("erroResultado", it.toString())
            },
            onFailure = {
                mensagemErro.postValue(R.string.error_api_connection.toString())
                barraProgresso.postValue(false)
            }
        )
    }
}