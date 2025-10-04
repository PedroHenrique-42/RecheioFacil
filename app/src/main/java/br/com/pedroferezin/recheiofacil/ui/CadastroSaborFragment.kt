package br.com.pedroferezin.recheiofacil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.pedroferezin.recheiofacil.databinding.FragmentCadastroSaborBinding
import br.com.pedroferezin.recheiofacil.domain.SaborPastel
import br.com.pedroferezin.recheiofacil.viewmodel.SaborPastelViewModel
import br.com.pedroferezin.recheiofacil.viewmodel.states.CadastroState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CadastroSaborFragment : Fragment() {
    lateinit var binding: FragmentCadastroSaborBinding

    val viewModel: SaborPastelViewModel by viewModels {
        SaborPastelViewModel.saborPastelViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCadastroSaborBinding.inflate(inflater, container, false)
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateCadastro.collect {
                when (it) {
                    CadastroState.Success -> Snackbar.make(
                        binding.root,
                        "Sabor de pastel inserido com sucesso!",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    CadastroState.Loading -> {}
                }
            }
        }

        binding.buttonSalvar.setOnClickListener {
            if (validarCampos()) {
                with(binding.saboresFieldsLayout) {
                    val saborPastel = SaborPastel(
                        nome = editTextNome.text.toString(),
                        descricao = editTextDescricao.text.toString(),
                        preco = editTextPreco.text.toString().toDouble(),
                        disponivel = checkboxDisponivel.isChecked
                    )
                    viewModel.insert(saborPastel)
                    findNavController().navigateUp()
                }
            }
        }
    }
}