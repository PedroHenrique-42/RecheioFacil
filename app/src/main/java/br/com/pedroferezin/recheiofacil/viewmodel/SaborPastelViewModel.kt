package br.com.pedroferezin.recheiofacil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import br.com.pedroferezin.recheiofacil.RecheioFacilApplication
import br.com.pedroferezin.recheiofacil.domain.SaborPastel
import br.com.pedroferezin.recheiofacil.repository.SaborPastelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CadastroState {
    data object Success : CadastroState()
    data object Loading : CadastroState()
}

class SaborPastelViewModel(private val repository: SaborPastelRepository) : ViewModel() {
    private val _stateCadastro = MutableStateFlow<CadastroState>(CadastroState.Loading)
    val stateCadastro = _stateCadastro.asStateFlow()

    fun insert(saborPastel: SaborPastel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(saborPastel)
        _stateCadastro.value = CadastroState.Success
    }

    companion object {
        fun saborPastelViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application = checkNotNull(
                        extras[APPLICATION_KEY]
                    )
                    return SaborPastelViewModel(
                        (application as RecheioFacilApplication).repository
                    ) as T
                }
            }
    }
}