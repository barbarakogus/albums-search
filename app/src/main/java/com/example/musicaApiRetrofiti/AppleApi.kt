package com.example.musicaApiRetrofiti

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//3 especificar as chamadas de API - definindo as regras da requisicao conforme a documentacao da api
interface AppleApi {

    @GET("/search") //informamos o método HTTP utilizado e o endpoint utilizado pra fazer a req.
    fun buscarAlbumPorPalavraChave(
        @Query("term") term: String, //o query vai enviar os parametros para API, parametros já definidos na documentacao, qm define os parametros é o backend
        @Query("entity") entity: String //os parametros que estao sendo passados como term e entity, säo os que definimos na val callBuscaAlbums (Jack Johson e albums)
    ) : Call<ResultadoAlbums>

    @GET("/search")
    fun buscarFaixas(
        @Query("term") term: String, //o query vai enviar os parametros para API, parametros já definidos na documentacao, qm define os parametros é o backend
        @Query("entity") entity: String, //os parametros que estao sendo passados como term e entity, säo os que definimos na val callBuscaAlbums (Jack Johson e albums)
        @Query("attribute") attribute: String
    ) : Call<ResultadoAlbums>

}

// https://itunes.apple.com/search\?term\=in+between+dreams\&entity\=song\&attribute\=albumTerm

//Conforme a documentacao a interface obrigatoriamente devolverá uma Call, que é a entidade que representará nossa req. e permitirá q ela seja executada,
//toda vez q a devolvemos precisamos indicar q tipo de retorno é esperado. Conforme a documentacao Retrofit devemos retornar um objeto, no nosso caso
//esperamos um objeto composto por uma lista de albums.
//call utilizamos qdo trabalhamos com callback. Quando trabalhamos com courotines nao precisamos chamar Call.

//conforme documentacao do retrofit a definicao das regras de uma requisicao deve sempre ser feita em uma Interface. Em um msmo projeto podemos ter mais de uma.
//essa api q estamos trabalhando nesse projeto devolve um objeto.