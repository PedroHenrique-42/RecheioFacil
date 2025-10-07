package br.com.pedroferezin.recheiofacil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.pedroferezin.recheiofacil.R
import br.com.pedroferezin.recheiofacil.databinding.FragmentListaSaboresBinding
import br.com.pedroferezin.recheiofacil.domain.SaborPastel
import br.com.pedroferezin.recheiofacil.ui.adapter.ListaSaboresAdapter
import br.com.pedroferezin.recheiofacil.ui.adapter.OnSaborPastelClickListener
import br.com.pedroferezin.recheiofacil.viewmodel.SaborPastelViewModel
import br.com.pedroferezin.recheiofacil.viewmodel.states.ListaState
import kotlinx.coroutines.launch

class ListaSaboresFragment : Fragment(), OnSaborPastelClickListener {
    lateinit var binding: FragmentListaSaboresBinding
    lateinit var listaSaboresAdapter: ListaSaboresAdapter
    private val viewModel: SaborPastelViewModel by viewModels { SaborPastelViewModel.saborPastelViewModelFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaSaboresBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listaSaboresFragment_to_cadastroSaborFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaSaboresAdapter = ListaSaboresAdapter(this)
        binding.recyclerview.adapter = listaSaboresAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateList.collect { state ->
                when (state) {
                    is ListaState.Success -> {
                        binding.textEmptyList.visibility = View.GONE
                        listaSaboresAdapter.submitList(state.sabores)
                    }

                    ListaState.Empty -> {
                        binding.textEmptyList.visibility = View.VISIBLE
                        listaSaboresAdapter.submitList(emptyList())
                    }

                    ListaState.Loading -> {}
                }
            }
        }

        viewModel.getAllSabores()
    }

    override fun onSaborPastelClick(id: Int) {
        Toast.makeText(this.context, "Clicando ${id}", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteSaborPastel(saborPastel: SaborPastel) {
        viewModel.delete(saborPastel)
    }

    override fun onEditSaborPastel(id: Int) {
        val bundle = Bundle()
        bundle.putInt("idSaborPastel", id)
        findNavController().navigate(
            R.id.action_listaSaboresFragment_to_edicaoSaborFragment,
            bundle
        )
    }
}