package br.com.pedroferezin.recheiofacil.domain

import br.com.pedroferezin.recheiofacil.data.SaborPastelEntity

data class SaborPastel(
    val id: Int = 0,
    val nome: String,
    val descricao: String,
    val preco: Double,
    val disponivel: Boolean,
) {
    fun toEntity(): SaborPastelEntity {
        return SaborPastelEntity(
            id,
            nome,
            descricao,
            preco,
            disponivel
        )
    }
}