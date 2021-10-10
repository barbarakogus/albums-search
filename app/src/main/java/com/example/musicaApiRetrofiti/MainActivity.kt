package com.example.musicaApiRetrofiti

/*melhorias
-layout
-arrumar texto invandindo espaco da imagem
-colocar o click em todos os elementos da linha
-terceira tela com a letra da musica
-binding view segunda tela
-mudar cor barra progresso
-borda imagem
-borda button
-refinar a busca da API
-*/


import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicaApiRetrofiti.databinding.ActivityMainBinding
import retrofit2.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val mainViewModel by viewModels<MainViewModel>() //estamos instanciando o ViewModel. Só conseguimos fazer dessa forma, pois implementamos a biblioteca lifecycle-livedata-ktx

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Buscar Albums - AppleApi"

        binding.btnBuscarAlbumLayout.setOnClickListener {
            setObserver()
            mainViewModel.buscarAlbuns(binding.inputNomeCantorLayout.editText?.text.toString())
            closeKeyboard()
        }
    }

    //aqui estamos configurando os observer. Estamos pegando o mutableLivedata, q foi declarado no MainViewModel, e vamos atribuir um obseve
    //para cada mutableLivedata. O observe vai ser responsável por pegar a notificacao enviada pelo postValue, e apartir disso vai executar o codigo dentro das {}.
    private fun setObserver() {
        mainViewModel.albums.observe(this, {
            val adapter = AdapterAlbum(it.results, onClickAlbum = { nomeAlbum ->
                val goToAlbumSelecionado = Intent(this@MainActivity, MainActivity2::class.java)
                goToAlbumSelecionado.putExtra("nomeAlbum", nomeAlbum)
                goToAlbumSelecionado.putExtra("nomeArtista", binding.inputNomeCantorLayout.editText?.text.toString())
                this@MainActivity.startActivity(goToAlbumSelecionado)
                Log.d("nomeAlbum", nomeAlbum)
            })

            if (it.results.isNotEmpty()) {
                binding.rvAlbumsLayout.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.rvAlbumsLayout.adapter = adapter
                binding.mensagemErroLayout.text = ""
            } else {
                mostrarMensagemErro("Resultado nao encontrado")
                binding.rvAlbumsLayout.adapter = null
            } //qm alimenta o RV é o adapter. Logo desacoplamos o adapter do RV na linha anterior, e dessa forma o RV n tem dados para carregar.
        })
        mainViewModel.mensagemErro.observe(this, {
            mostrarMensagemErro(it)
        })
        mainViewModel.barraProgresso.observe(this, {
            configurarBarraProgresso(it)
        })
    } //this = está informando o ciclo de vida da activity(the observer neste projeto). O liveData tem conhecimento sobre o ciclo de vida dos componetes do android.

    fun mostrarMensagemErro(mensagem: String) {
        binding.mensagemErroLayout.text = mensagem
    }

    fun configurarBarraProgresso(valor: Boolean) {
        if (valor) {
            binding.barraProgressoLayout.visibility = View.VISIBLE
        } else {
            binding.barraProgressoLayout.visibility = View.INVISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun closeKeyboard() {
        // this will give us the view which is currently focus in this layout
        val view = this.currentFocus

        // if nothing is currently focus then this will protect the app from crash
        if (view != null) {
            // now assign the system service to InputMethodManager
            val manager: InputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            manager
                .hideSoftInputFromWindow(
                    view.windowToken, 0
                )
        }
    }
}
