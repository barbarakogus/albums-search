package com.example.musicaApiRetrofiti

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ModelLyrics {

    val vagalumeApi = configurarRetrofit()

    fun buscarLyricsApi (
        nomeCantor : String,
        nomeMusica : String,
        onSuccess : (ResultadoLyrics) -> Unit,
        onFailure : () -> Unit) {

        //5 especifica a chamada (call) e os seus parametros
        val callBuscaAlbums = vagalumeApi.buscarLetraMusica(nomeCantor, nomeMusica)

        //enfileirar. é nesse momento q estamos fazendo a chamada para o servdor, e pegar o resultado
        //enqueue - colocar na fila
        callBuscaAlbums.enqueue(object : Callback<ResultadoLyrics> {
            override fun onResponse(
                //6 passo: avaliar o que fazer quando der sucesso
                call: Call<ResultadoLyrics>,
                response: Response<ResultadoLyrics>
            ) {
                val resultado = response.body()
                resultado?.let {
                    onSuccess(resultado)
                }
            }
            override fun onFailure(call: Call<ResultadoLyrics>, t: Throwable) {
                //7 passo: avaliar o que fazer quando der erro
                onFailure()
            }
        })
    }

    fun configurarRetrofit() : VagalumeApi {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        //4 inicializar o retrofit
        val retrofit = Retrofit.Builder() //a documentacao exige criar uma instancia do retrofit (oq fizemos nessa linha) junto com o Builder(), além da configuracao de um service --> AppleApi
            .baseUrl("https://api.vagalume.com.br/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //vai retornar a instancia do retrofit
        val api = retrofit.create(VagalumeApi::class.java) //criando um objeto do tipo retrofit AppleApi
        //a partir da instancia do retrofit precisamos disponibilizar/criar uma instancia do nosso service, ou seja da nossa AppleAPI. é desse modo que vamos(conseguimos?) utilizá-la
        return api
    }
}


