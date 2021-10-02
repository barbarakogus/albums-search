package com.example.musicaApiRetrofiti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvListaAlbums = findViewById<RecyclerView>(R.id.rv_albums)

        val appleApi = configurarRetrofit()

        //5 especifica a chamada (call) e os seus parametros
        val callBuscaAlbums = appleApi.buscarAlbumPorPalavraChave(
            "Jack Johson",
            "album"
        )

        //enfileirar. é nesse momento q estamos fazendo a chamada para o servdor, e pegar o resultado
        callBuscaAlbums.enqueue(object : Callback<ResultadoAlbums> {
            override fun onResponse(
                //6 passo: avaliar o que fazer quando der sucesso
                call: Call<ResultadoAlbums>,
                response: Response<ResultadoAlbums>
            ) {
                val resultado = response.body()
                resultado?.let {
                    //se a api der certo configuramos o RV
                    val adapter = AlbumAdapter(it.results)
                    rvListaAlbums.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false )
                    rvListaAlbums.adapter = adapter
                }
            }
            override fun onFailure(call: Call<ResultadoAlbums>, t: Throwable) {
                //7 passo: avaliar o que fazer quando der erro
                Toast.makeText(this@MainActivity, "Erro ao chamar API", Toast.LENGTH_LONG).show()
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
