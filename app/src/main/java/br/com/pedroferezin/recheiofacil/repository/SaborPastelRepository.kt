package br.com.pedroferezin.recheiofacil.repository

import br.com.pedroferezin.recheiofacil.data.SaborPastelDAO
import br.com.pedroferezin.recheiofacil.domain.SaborPastel

class SaborPastelRepository(private val saborPastelDAO: SaborPastelDAO) {
    suspend fun insert(saborPastel: SaborPastel) {
        saborPastelDAO.insert(saborPastel.toEntity());
    }
}