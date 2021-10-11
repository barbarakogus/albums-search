package com.example.musicaApiRetrofiti

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicaApiRetrofiti.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var bindingActivity2: ActivityMain2Binding

    val mainViewModelActivity2 by viewModels<MainViewModel>()

    var nomeAlbumExtra: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity2 = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(bindingActivity2.root)

        nomeAlbumExtra = intent.extras?.getString("nomeAlbum")
        bindingActivity2.nomeAlbumLayoutActivity2.text = nomeAlbumExtra

        val nomeArtista = intent.extras?.getString("nomeArtista")

        setObserver()
        mainViewModelActivity2.buscarFaixas(nomeAlbumExtra.toString())
    }

    fun setObserver() {
        mainViewModelActivity2.albums.observe(this, {
            Log.d("it", it.results.first().trackName)
            val faixas = it.results.filter { it.collectionName.equals(nomeAlbumExtra) }
            val faixasSorted = faixas.sortedBy { it.trackNumber }
            val adapterFaixas = AdapterFaixas(faixasSorted)
            bindingActivity2.rvFaixasLayoutActivity2.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            bindingActivity2.rvFaixasLayoutActivity2.adapter = adapterFaixas
        })
    }
}

