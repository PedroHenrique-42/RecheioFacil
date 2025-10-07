package br.com.pedroferezin.recheiofacil.viewmodel.states

import br.com.pedroferezin.recheiofacil.domain.SaborPastel

sealed class BuscarSaborState {
    data class Success(val sabor: SaborPastel) : BuscarSaborState()
    data object Loading : BuscarSaborState()
}