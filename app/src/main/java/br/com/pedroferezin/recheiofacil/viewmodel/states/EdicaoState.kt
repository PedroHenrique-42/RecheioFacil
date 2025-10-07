package br.com.pedroferezin.recheiofacil.viewmodel.states

sealed class EdicaoState {
    data object Success : EdicaoState()
    data object Loading : EdicaoState()
}