package com.example.musicaApiRetrofiti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicaApiRetrofiti.databinding.LayoutLinhaFaixasBinding

class AdapterFaixas(val faixas : List<Album>) : RecyclerView.Adapter<AdapterFaixas.ViewHolderFaixas>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFaixas {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutLinhaFaixasBinding.inflate(layoutInflater, parent, false)

        return ViewHolderFaixas(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderFaixas, position: Int) {
        holder.preencherListaFaixa(faixas[position])
    }

    override fun getItemCount(): Int {
        return faixas.size
    }

    class ViewHolderFaixas(val binding: LayoutLinhaFaixasBinding) : RecyclerView.ViewHolder(binding.root) {

        fun preencherListaFaixa(faixa : Album) {
            binding.numeroFaixaLayout.text = faixa.trackNumber.toString()
            binding.nomeFaixaLayout.text = faixa.trackName
        }
    }
}