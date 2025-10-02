package br.com.pedroferezin.recheiofacil.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.pedroferezin.recheiofacil.domain.SaborPastel

@Entity(tableName = "recheios")
data class SaborPastelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val descricao: String,
    val preco: Double,
    val disponivel: Boolean
) {
    fun toDomain(): SaborPastel {
        return SaborPastel(
            id,
            nome,
            descricao,
            preco,
            disponivel
        )
    }
}