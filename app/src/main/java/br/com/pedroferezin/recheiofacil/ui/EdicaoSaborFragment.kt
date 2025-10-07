package br.com.pedroferezin.recheiofacil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.pedroferezin.recheiofacil.databinding.FragmentEdicaoSaborBinding
import br.com.pedroferezin.recheiofacil.domain.SaborPastel
import br.com.pedroferezin.recheiofacil.viewmodel.SaborPastelViewModel
import br.com.pedroferezin.recheiofacil.viewmodel.states.BuscarSaborState.Loading
import br.com.pedroferezin.recheiofacil.viewmodel.states.BuscarSaborState.Success
import br.com.pedroferezin.recheiofacil.viewmodel.states.EdicaoState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class EdicaoSaborFragment : Fragment() {
    lateinit var binding: FragmentEdicaoSaborBinding

    val viewModel: SaborPastelViewModel by viewModels {
        SaborPastelViewModel.saborPastelViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEdicaoSaborBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun validarCampos(): Boolean = with(binding.saboresFieldsLayout) {
        val campos = listOf(editTextNome, editTextDescricao, editTextPreco)
        val invalido = campos.firstOrNull { it.text.isNullOrBlank() }?.apply {
            error = "Campo obrigatório"
            requestFocus()
            Snackbar.make(binding.root, "Preencha todos os campos.", Snackbar.LENGTH_LONG).show()
        }
        campos.filterNot { it == invalido }.forEach { it.error = null }
        invalido == null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idSaborPastel = requireArguments().getInt("idSaborPastel")
        viewModel.getSabor(idSaborPastel)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateBuscarSabor.collect {
                when (it) {
                    is Success -> {
                        binding.apply {
                            saboresFieldsLayout.editTextNome.setText(it.sabor.nome)
                            saboresFieldsLayout.editTextDescricao.setText(it.sabor.descricao)
                            saboresFieldsLayout.editTextPreco.setText(it.sabor.preco.toString())
                            saboresFieldsLayout.checkboxDisponivel.isChecked = it.sabor.disponivel
                        }
                    }

                    Loading -> {}
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateEdicao.collect {
                when (it) {
                    EdicaoState.Success -> {
                        Snackbar.make(
                            binding.root,
                            "Sabor de pastel editado com sucesso!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().navigateUp()
                    }

                    EdicaoState.Loading -> {}
                }
            }
        }

        binding.buttonEditar.setOnClickListener {
            if (validarCampos()) {
                with(binding.saboresFieldsLayout) {
                    val saborPastel = SaborPastel(
                        id = idSaborPastel,
                        nome = editTextNome.text.toString(),
                        descricao = editTextDescricao.text.toString(),
                        preco = editTextPreco.text.toString().toDouble(),
                        disponivel = checkboxDisponivel.isChecked
                    )
                    viewModel.update(saborPastel)
                }
            }
        }

    }

}