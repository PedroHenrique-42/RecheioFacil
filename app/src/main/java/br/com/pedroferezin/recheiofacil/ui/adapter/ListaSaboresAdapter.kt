package br.com.pedroferezin.recheiofacil.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.pedroferezin.recheiofacil.databinding.SaborPastelCelulaBinding
import br.com.pedroferezin.recheiofacil.domain.SaborPastel


class ListaSaboresAdapter(
    private val onSaborPastelClickListener: OnSaborPastelClickListener
) : ListAdapter<SaborPastel, ListaSaboresAdapter.ViewHolder>(SaborPastelDiffCallback()) {
    private lateinit var viewBinding: SaborPastelCelulaBinding

    inner class ViewHolder(viewBinding: SaborPastelCelulaBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        val nameTextView: TextView = viewBinding.nome
        val precoTextView: TextView = viewBinding.preco

        init {
            viewBinding.iconExcluir.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(adapterPosition)
                    onSaborPastelClickListener.onDeleteSaborPastel(item)
                }
            }
            viewBinding.iconEditar.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(adapterPosition)
                    onSaborPastelClickListener.onEditSaborPastel(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        viewBinding =
            SaborPastelCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
        getItem(position).let { saborPastel ->
            holder.nameTextView.text = saborPastel.nome
            "R$ %.2f".format(saborPastel.preco).also {
                holder.precoTextView.text = it
            }
        }
    }
}

class SaborPastelDiffCallback : DiffUtil.ItemCallback<SaborPastel>() {
    override fun areItemsTheSame(oldItem: SaborPastel, newItem: SaborPastel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SaborPastel, newItem: SaborPastel): Boolean {
        return oldItem == newItem
    }
}