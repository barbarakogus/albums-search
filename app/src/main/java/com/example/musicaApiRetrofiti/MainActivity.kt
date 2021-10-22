package com.example.musicaApiRetrofiti

/*melhorias
-terceira tela com a letra da musica
-borda imagem
-refinar a busca da API
-*/

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicaApiRetrofiti.databinding.ActivityMainBinding
import retrofit2.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val albumViewModel by viewModels<ViewModelAlbum>() //estamos instanciando o ViewModel. Só conseguimos fazer dessa forma, pois implementamos a biblioteca lifecycle-livedata-ktx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Search albums - AppleApi"

        binding.btnBuscarAlbumLayout.setOnClickListener {
            setObserver()
            albumViewModel.buscarAlbuns(binding.inputNomeCantorLayout.editText?.text.toString())
            closeKeyboard()
        }
    }

    //aqui estamos configurando os observer. Estamos pegando o mutableLivedata, q foi declarado no MainViewModel, e vamos atribuir um obseve
    //para cada mutableLivedata. O observe vai ser responsável por pegar a notificacao enviada pelo postValue, e apartir disso vai executar o codigo dentro das {}.
    private fun setObserver() {
        albumViewModel.albums.observe(this, {
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
                mostrarMensagemErro(getString(R.string.erro_result_not_found))
                binding.rvAlbumsLayout.adapter = null
            } //qm alimenta o RV é o adapter. Logo desacoplamos o adapter do RV na linha anterior, e dessa forma o RV n tem dados para carregar.
        })
        albumViewModel.mensagemErro.observe(this, {
            mostrarMensagemErro(it)
        })
        albumViewModel.barraProgresso.observe(this, {
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

    //@RequiresApi(Build.VERSION_CODES.M) -- para versao antiga suportar alguma ferramenta especifica. As vezes desnecessario, como neste caso.
    //Dependendo do app pode ate impedir o carregamento, qdo chamado antes do onCreate. Code smell!
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
