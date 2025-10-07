package br.com.pedroferezin.recheiofacil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.pedroferezin.recheiofacil.databinding.FragmentVisualizacaoSaborBinding
import br.com.pedroferezin.recheiofacil.domain.SaborPastel
import br.com.pedroferezin.recheiofacil.viewmodel.SaborPastelViewModel
import br.com.pedroferezin.recheiofacil.viewmodel.states.BuscarSaborState.Loading
import br.com.pedroferezin.recheiofacil.viewmodel.states.BuscarSaborState.Success
import kotlinx.coroutines.launch

class VisualizacaoSaborFragment : Fragment() {
    lateinit var binding: FragmentVisualizacaoSaborBinding

    val viewModel: SaborPastelViewModel by viewModels {
        SaborPastelViewModel.saborPastelViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVisualizacaoSaborBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun fillFields(saborPastel: SaborPastel) {
        binding.saboresFieldsLayout.apply {
            editTextNome.isEnabled = false
            editTextNome.setText(saborPastel.nome)

            editTextDescricao.isEnabled = false
            editTextDescricao.setText(saborPastel.descricao)

            editTextPreco.isEnabled = false
            editTextPreco.setText(saborPastel.preco.toString())

            checkboxDisponivel.isEnabled = false
            checkboxDisponivel.isChecked = saborPastel.disponivel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSabor(requireArguments().getInt("idSaborPastel"))
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateBuscarSabor.collect {
                when (it) {
                    is Success -> fillFields(it.sabor)
                    Loading -> {}
                }
            }
        }
    }
}