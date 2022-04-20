package com.example.musicaApiRetrofiti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicaApiRetrofiti.databinding.LayoutLinhaAlbumsBinding

class AdapterAlbum(val albums : List<Album>, val onClickAlbum: (String) -> Unit) : RecyclerView.Adapter<AdapterAlbum.AlbumViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutLinhaAlbumsBinding.inflate(layoutInflater, parent, false)

        return AlbumViewHolder(onClickAlbum, binding)
    }
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.preencherListaAlbum(albums[position])
    }
    override fun getItemCount(): Int {
        return albums.size
    }

    //t odo viewHolder está atrelado a view da linha. como estamos usando o viewBinding, dentro do viewBinding temos acesso a view atraves do .root
    //o .root representa a view dentro de um viewBinding
    class AlbumViewHolder(val onClickAlbum: (String) -> Unit, val binding: LayoutLinhaAlbumsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun preencherListaAlbum (album : Album) {
            binding.nomeArtista.text = album.artistName
            binding.nomeAlbum.text = album.collectionName
            binding.numeroFaixas.text = album.trackCount.toString()
            Glide.with(itemView.context).load(album.artworkUrl100).into(binding.imagemAlbum)

            binding.root.setOnClickListener {
                onClickAlbum(album.collectionName)
            }
        }
    }
}