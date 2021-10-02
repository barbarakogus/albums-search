package com.example.musicaApiRetrofiti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*

class MainActivity : AppCompatActivity() {

    var rvListaAlbums : RecyclerView? = null

    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvListaAlbums = findViewById(R.id.rv_albums)
        setObserver()
        mainViewModel.buscarAlbuns()
    }

    private fun setObserver() {
        mainViewModel.albums.observe(this, {
            val adapter = AlbumAdapter(it.results)
            rvListaAlbums?.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false )
            rvListaAlbums?.adapter = adapter
        })
    }

    fun onFailure() {
        Toast.makeText(this@MainActivity, "Erro ao chamar API", Toast.LENGTH_LONG).show()
    }
}
