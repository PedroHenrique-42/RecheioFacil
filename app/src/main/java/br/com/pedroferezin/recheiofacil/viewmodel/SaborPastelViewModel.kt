package br.com.pedroferezin.recheiofacil.viewmodel

import android.view.KeyEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import br.com.pedroferezin.recheiofacil.RecheioFacilApplication
import br.com.pedroferezin.recheiofacil.domain.SaborPastel
import br.com.pedroferezin.recheiofacil.repository.SaborPastelRepository
import br.com.pedroferezin.recheiofacil.viewmodel.states.BuscarSaborState
import br.com.pedroferezin.recheiofacil.viewmodel.states.CadastroState
import br.com.pedroferezin.recheiofacil.viewmodel.states.EdicaoState
import br.com.pedroferezin.recheiofacil.viewmodel.states.ListaState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SaborPastelViewModel(private val repository: SaborPastelRepository) : ViewModel() {
    private val _stateCadastro = MutableStateFlow<CadastroState>(CadastroState.Loading)
    val stateCadastro = _stateCadastro.asStateFlow()

    private val _stateEdicao = MutableStateFlow<EdicaoState>(EdicaoState.Loading)
    val stateEdicao = _stateEdicao.asStateFlow()
    private val _stateBuscarSabor =
        MutableStateFlow<BuscarSaborState>(value = BuscarSaborState.Loading)
    val stateBuscarSabor = _stateBuscarSabor.asStateFlow()

    private val _stateList = MutableStateFlow<ListaState>(ListaState.Loading)
    val stateList = _stateList.asStateFlow()

    fun insert(saborPastel: SaborPastel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(saborPastel)
        _stateCadastro.value = CadastroState.Success
    }

    fun update(saborPastel: SaborPastel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(saborPastel)
        _stateEdicao.value = EdicaoState.Success
    }

    fun delete(saborPastel: SaborPastel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(saborPastel)
    }

    fun getAllSabores() {
        viewModelScope.launch {
            repository.getAll().collect { result ->
                if (result.isEmpty()) {
                    _stateList.value = ListaState.Empty
                } else {
                    _stateList.value = ListaState.Success(result)
                }
            }
        }
    }

    fun getSabor(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val sabor = repository.getSaborById(id)
        _stateBuscarSabor.value = BuscarSaborState.Success(sabor)
    }

    companion object {
        fun saborPastelViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>, extras: CreationExtras
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