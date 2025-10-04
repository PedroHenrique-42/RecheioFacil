package br.com.pedroferezin.recheiofacil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.pedroferezin.recheiofacil.R
import br.com.pedroferezin.recheiofacil.databinding.FragmentListaSaboresBinding

class ListaSaboresFragment : Fragment() {
    lateinit var binding: FragmentListaSaboresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaSaboresBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listaSaborzesFragment_to_cadastroSaborFragment)
        }

        return binding.root
    }
}