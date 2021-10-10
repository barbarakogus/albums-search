package com.example.musicaApiRetrofiti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterFaixas(val faixas : List<Album>) : RecyclerView.Adapter<AdapterFaixas.ViewHolderFaixas>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFaixas {
        LayoutInflater.from(parent.context).inflate(R.layout.layout_linha_faixas, parent, false)
            .let {
                return ViewHolderFaixas(it)
            }
    }

    override fun onBindViewHolder(holder: ViewHolderFaixas, position: Int) {
        holder.preencherListaFaixa(faixas[position])
    }

    override fun getItemCount(): Int {
        return faixas.size
    }

    class ViewHolderFaixas(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val numeroFaixa = itemView.findViewById<TextView>(R.id.numero_faixa_layout)
        val nomeFaixa = itemView.findViewById<TextView>(R.id.nome_faixa_layout)

        fun preencherListaFaixa(faixa : Album) {
            numeroFaixa.text = faixa.trackNumber.toString()
            nomeFaixa.text = faixa.trackName
        }
    }
}