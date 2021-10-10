package com.example.musicaApiRetrofiti

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {

    val mainViewModelActivity2 by viewModels<MainViewModel>()

    var rvActivity2 : RecyclerView? = null
    var nomeAlbumExtra : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        rvActivity2 = findViewById(R.id.rv_faixas_layout_activity2)
        val nomeAlbum = findViewById<TextView>(R.id.nome_album_layout_activity2)
        nomeAlbumExtra = intent.extras?.getString("nomeAlbum")
        nomeAlbum.text = nomeAlbumExtra

        val nomeArtista = intent.extras?.getString("nomeArtista")

        setObserver()
        mainViewModelActivity2.buscarFaixas(nomeAlbumExtra.toString())
    }

    fun  setObserver() {
        mainViewModelActivity2.albums.observe(this, {
            Log.d("it", it.results.first().trackName)
            val adapterFaixas = AdapterFaixas(it.results)
            rvActivity2?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rvActivity2?.adapter = adapterFaixas
        })
    }
}

