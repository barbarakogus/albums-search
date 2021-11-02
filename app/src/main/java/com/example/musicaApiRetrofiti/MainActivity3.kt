package com.example.musicaApiRetrofiti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.musicaApiRetrofiti.databinding.ActivityMain2Binding
import com.example.musicaApiRetrofiti.databinding.ActivityMain3Binding
import com.example.musicaApiRetrofiti.databinding.ActivityMainBinding

class MainActivity3 : AppCompatActivity() {

    private lateinit var bindingActivity3: ActivityMain3Binding

    var trackName: String? = null

    val LyricsViewModel by viewModels<ViewModelLyrics>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity3 = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(bindingActivity3.root)

        var nomeArtista = intent.extras?.getString("nomeArtista")
        trackName = intent.extras?.getString("trackName")
        bindingActivity3.songName.text = trackName

        setObserver()
        LyricsViewModel.buscarLyrics(nomeArtista.toString(), trackName.toString())
    }

    fun setObserver() {

        LyricsViewModel.lyrics.observe(this, {
            bindingActivity3.lyrics.text = it.mus.firstOrNull()?.lyrics
        })
        LyricsViewModel.barraProgresso.observe(this) {
            configurarBarraProgresso(it)
        }
    }

    fun configurarBarraProgresso(valor: Boolean) {
        if (valor) {
            bindingActivity3.barraProgressoLayout3.visibility = View.VISIBLE
        } else {
            bindingActivity3.barraProgressoLayout3.visibility = View.INVISIBLE
        }
    }
}

