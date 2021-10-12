package com.example.musicaApiRetrofiti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicaApiRetrofiti.databinding.LayoutLinhaAlbumsBinding
import com.example.musicaApiRetrofiti.databinding.LayoutLinhaFaixasBinding

class AdapterFaixas(val faixas : List<Album>) : RecyclerView.Adapter<AdapterFaixas.ViewHolderFaixas>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFaixas {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutLinhaFaixasBinding.inflate(layoutInflater, parent, false)

        LayoutInflater.from(parent.context).inflate(R.layout.layout_linha_faixas, parent, false)
            .let {
                return ViewHolderFaixas(binding)
            }
    }

    override fun onBindViewHolder(holder: ViewHolderFaixas, position: Int) {
        holder.preencherListaFaixa(faixas[position])
    }

    override fun getItemCount(): Int {
        return faixas.size
    }

    class ViewHolderFaixas(val binding: LayoutLinhaFaixasBinding) : RecyclerView.ViewHolder(binding.root) {

        fun preencherListaFaixa(faixa : Album) {
            binding.nomeFaixaLayout.text = faixa.trackNumber.toString()
            binding.nomeFaixaLayout.text = faixa.trackName
        }
    }
}