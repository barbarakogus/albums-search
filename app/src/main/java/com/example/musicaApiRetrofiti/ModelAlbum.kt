package com.example.musicaApiRetrofiti

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//aqui tenho q ter o codigo q vai se conectar com a API
class ModelAlbum {

    val appleApi = configurarRetrofit()

    fun buscarAlbumApi (
        term : String,
        onSuccess : (ResultadoAlbums) -> Unit,
        onFailure : () -> Unit) {

        //5 especifica a chamada (call) e os seus parametros
        val callBuscaAlbums = appleApi.buscarAlbumPorPalavraChave(
            term,
            "album"
        )

        //enfileirar. é nesse momento q estamos fazendo a chamada para o servdor, e pegar o resultado
        //enqueue - colocar na fila
        callBuscaAlbums.enqueue(object : Callback<ResultadoAlbums> {
            override fun onResponse(
                //6 passo: avaliar o que fazer quando der sucesso
                call: Call<ResultadoAlbums>,
                response: Response<ResultadoAlbums>
            ) {
                val resultado = response.body()
                resultado?.let {
                    onSuccess(resultado)
                }
            }
            override fun onFailure(call: Call<ResultadoAlbums>, t: Throwable) {
                //7 passo: avaliar o que fazer quando der erro
                onFailure()
            }
        })
    }

    fun configurarRetrofit() : AppleApi { //vai retornar uma implementacao da interface AppleApi
        //4 inicializar o retrofit
        val retrofit = Retrofit.Builder() //a documentacao exige criar uma instancia do retrofit (oq fizemos nessa linha) junto com o Builder(), além da configuracao de um service --> AppleApi
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build() //vai retornar a instancia do retrofit
        val api = retrofit.create(AppleApi::class.java) //criando um objeto do tipo retrofit AppleApi
        //a partir da instancia do retrofit precisamos disponibilizar/criar uma instancia do nosso service, ou seja da nossa AppleAPI. é desse modo que vamos(conseguimos?) utilizá-la
        return api
    }
}