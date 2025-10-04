package br.com.pedroferezin.recheiofacil.viewmodel.states

import br.com.pedroferezin.recheiofacil.domain.SaborPastel

sealed class ListaState {
    data class Success(val sabores: List<SaborPastel>) : ListaState()
    data object Loading : ListaState()
    data object Empty : ListaState()
}