package br.com.pedroferezin.recheiofacil.viewmodel.states

sealed class CadastroState {
    data object Success : CadastroState()
    data object Loading : CadastroState()
}