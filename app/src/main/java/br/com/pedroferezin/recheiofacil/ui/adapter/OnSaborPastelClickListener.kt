package br.com.pedroferezin.recheiofacil.ui.adapter

import br.com.pedroferezin.recheiofacil.domain.SaborPastel

interface OnSaborPastelClickListener {
    fun onSaborPastelClick(position: Int)

    fun onDeleteSaborPastel(saborPastel: SaborPastel)

    fun onEditSaborPastel(saborPastel: SaborPastel)
}