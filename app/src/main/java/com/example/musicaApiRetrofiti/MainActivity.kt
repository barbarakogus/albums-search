package com.example.musicaApiRetrofiti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicaApiRetrofiti.databinding.ActivityMainBinding
import retrofit2.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val mainViewModel by viewModels<MainViewModel>() //estamos instanciando o ViewModel. Só conseguimos fazer dessa forma, pois implementamos a biblioteca lifecycle-livedata-ktx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Buscar Albums - AppleApi"

        binding.btnBuscarAlbumLayout.setOnClickListener {
            setObserver()
            mainViewModel.buscarAlbuns(binding.inputNomeCantorLayout.editText?.text.toString())
        }
    }

    //aqui estamos configurando os observer. Estamos pegando o mutableLivedata, q foi declarado no MainViewModel, e vamos atribuir um obseve
    //para cada mutableLivedata. O observe vai ser responsável por pegar a notificacao enviada pelo postValue, e apartir disso vai executar o codigo dentro das {}.
    private fun setObserver() {
        mainViewModel.albums.observe(this, {
            val adapter = AlbumAdapter(it.results)
            if (it.results.isNotEmpty()) {
                binding.rvAlbumsLayout.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.rvAlbumsLayout.adapter = adapter
                binding.mensagemErroLayout.text = ""
            } else {
                mostrarMensagemErro("Resultado nao encontrado")
                binding.rvAlbumsLayout.adapter = null
            }
        })
        mainViewModel.mensagemErro.observe(this, {
            mostrarMensagemErro(it)
        })
        mainViewModel.barraProgresso.observe(this, {
            configurarBarraProgresso(it)
        })
    } //this = está informando o ciclo de vida da activity. O liveData tem conhecimento sobre o ciclo de vidas dos componetes do android.

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
}
