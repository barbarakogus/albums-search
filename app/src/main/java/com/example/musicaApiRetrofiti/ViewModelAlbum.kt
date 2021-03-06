package com.example.musicaApiRetrofiti

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelAlbum : ViewModel() {

    private val model = ModelAlbum() //instanciando o MainModel

    val albums: MutableLiveData<ResultadoAlbums> = MutableLiveData() //instanciando um objeto MutableLiveData, que é do tipo MitableLiveData, onde os dados sao do tipo ResultadoAlbum
    val mensagemErro: MutableLiveData<String> = MutableLiveData()
    val barraProgresso: MutableLiveData<Boolean> = MutableLiveData()

    fun buscarAlbuns(term : String) {
        barraProgresso.postValue(true)
        model.buscarAlbumApi(term,
            onSuccess = {
                albums.postValue(it)
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
//estamos trabalhando com Retrofit e a APppleApi. Analisando estes dois recursos, percebemos que o onSucesso trata o sucesso e tb qdo um enviamos um dado errado.
//neste caso a api retorna um [] vazio. E onFailure ficou restrito a qdo temos um erro de conexao com a API.