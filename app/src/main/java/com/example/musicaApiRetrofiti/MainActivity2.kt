package com.example.musicaApiRetrofiti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicaApiRetrofiti.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var bindingActivity2: ActivityMain2Binding

    val FaixasViewModel by viewModels<ViewModelFaixas>()

    var nomeAlbumExtra: String? = null
    var nomeArtista: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity2 = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(bindingActivity2.root)

        nomeArtista = intent.extras?.getString("nomeArtista")
        nomeAlbumExtra = intent.extras?.getString("nomeAlbum")
        bindingActivity2.nomeAlbumLayoutActivity2.text = nomeAlbumExtra

        setObserver()
        FaixasViewModel.buscarFaixas(nomeAlbumExtra.toString())
    }

    fun setObserver() {
        FaixasViewModel.albums.observe(this, {
            Log.d("it", it.results.first().trackName)

            val faixas = it.results.filter { it.collectionName.equals(nomeAlbumExtra) }
            val faixasSorted = faixas.sortedBy { it.trackNumber }

            val adapterFaixas = AdapterFaixas(faixasSorted, onClickFaixas = { trackName ->
                    val goToSongSelected = Intent(this@MainActivity2, MainActivity3::class.java)
                    goToSongSelected.putExtra("trackName", trackName)
                    goToSongSelected.putExtra("nomeArtista", nomeArtista)
                    this@MainActivity2.startActivity(goToSongSelected)
                },
            )
            bindingActivity2.rvFaixasLayoutActivity2.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            bindingActivity2.rvFaixasLayoutActivity2.adapter = adapterFaixas
        })
        FaixasViewModel.barraProgresso.observe(this){
            configurarBarraProgresso(it)
        }
    }

    fun configurarBarraProgresso(valor: Boolean) {
        if (valor) {
            bindingActivity2.barraProgressoLayout2.visibility = View.VISIBLE
        } else {
            bindingActivity2.barraProgressoLayout2.visibility = View.INVISIBLE
        }
    }
}

