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
import br.com.pedroferezin.recheiofacil.viewmodel.CadastroState
import br.com.pedroferezin.recheiofacil.viewmodel.SaborPastelViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateCadastro.collect {
                when (it) {
                    CadastroState.Success -> Snackbar.make(
                        binding.root,
                        "Sabor de pastel inserido com sucesso!",
                        Snackbar.LENGTH_SHORT
                    )

                    CadastroState.Loading -> {}
                }
            }
        }

        binding.buttonSalvar.setOnClickListener {
            val saborPastel = SaborPastel(
                nome = binding.saboresFieldsLayout.editTextNome.text.toString(),
                descricao = binding.saboresFieldsLayout.editTextDescricao.text.toString(),
                preco = binding.saboresFieldsLayout.editTextPreco.text.toString().toDouble(),
                disponivel = binding.saboresFieldsLayout.checkboxDisponivel.isChecked
            )

            viewModel.salvarSabor(saborPastel);
            findNavController().navigateUp()
        }
    }
}