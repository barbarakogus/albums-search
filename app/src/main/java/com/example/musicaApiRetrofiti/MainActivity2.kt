package com.example.musicaApiRetrofiti

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity2 = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(bindingActivity2.root)

        nomeAlbumExtra = intent.extras?.getString("nomeAlbum")
        bindingActivity2.nomeAlbumLayoutActivity2.text = nomeAlbumExtra

        val nomeArtista = intent.extras?.getString("nomeArtista")

        setObserver()
        FaixasViewModel.buscarFaixas(nomeAlbumExtra.toString())
    }

    fun setObserver() {
        FaixasViewModel.albums.observe(this, {
            Log.d("it", it.results.first().trackName)
            val faixas = it.results.filter { it.collectionName.equals(nomeAlbumExtra) }
            val faixasSorted = faixas.sortedBy { it.trackNumber }
            val adapterFaixas = AdapterFaixas(faixasSorted)
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

