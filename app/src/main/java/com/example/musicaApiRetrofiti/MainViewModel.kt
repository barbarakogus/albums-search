package com.example.musicaApiRetrofiti

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val model = MainModel() //instanciando o MainModel

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
                mensagemErro.postValue("Erro na conexão com a Api")
                barraProgresso.postValue(false)
            }
        )
    }

    fun buscarFaixas(term : String) {
        model.buscarFaixasApi(term,
            onSuccess = {
                albums.postValue(it)
                Log.d("api", it.toString())
                barraProgresso.postValue(false)
                Log.d("erroResultado", it.toString())
            },
            onFailure = {
                mensagemErro.postValue("Erro na conexão com a Api")
                barraProgresso.postValue(false)
            }
        )
    }
}
//estamos trabalhando com Retrofit e a APppleApi. Analisando estes dois recursos, percebemos que o onSucesso trata o sucesso e tb qdo um enviamos um dado errado.
//neste caso a api retorna um [] vazio. E onFailure ficou restrito a qdo temos um erro de conexao com a API.