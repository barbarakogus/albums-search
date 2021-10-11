package com.example.musicaApiRetrofiti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicaApiRetrofiti.databinding.ActivityMainBinding

class AdapterAlbum(val albums : List<Album>, val onClickAlbum: (String) -> Unit) : RecyclerView.Adapter<AdapterAlbum.AlbumViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        //val binding = ActivityMainBinding.inflate(layoutInflater, R.layout.layout_linha_albums, parent, false)

        LayoutInflater.from(parent.context).inflate(R.layout.layout_linha_albums, parent, false)
            .let {
                return AlbumViewHolder(it, onClickAlbum, /*binding*/)
            }
    }
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.preencherListaAlbum(albums[position])
    }
    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(itemView: View, val onClickAlbum: (String) -> Unit, /*val binding: ActivityMainBinding*/) : RecyclerView.ViewHolder(itemView) {
        val nomeArtista = itemView.findViewById<TextView>(R.id.nome_artista)
        val nomeAlbum = itemView.findViewById<TextView>(R.id.nome_album)
        val numeroFaixas = itemView.findViewById<TextView>(R.id.numero_faixas)
        val capaAlbum = itemView.findViewById<ImageView>(R.id.imagem_album)

        fun preencherListaAlbum (album : Album) {
            nomeArtista.text = album.artistName
            nomeAlbum.text = album.collectionName
            numeroFaixas.text = album.trackCount.toString()
            Glide.with(itemView.context).load(album.artworkUrl100).into(capaAlbum)

            itemView.setOnClickListener {
                onClickAlbum(album.collectionName)
            }
        }
    }
}