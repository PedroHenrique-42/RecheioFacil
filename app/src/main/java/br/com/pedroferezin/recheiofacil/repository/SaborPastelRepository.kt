package br.com.pedroferezin.recheiofacil.repository

import br.com.pedroferezin.recheiofacil.data.SaborPastelDAO
import br.com.pedroferezin.recheiofacil.domain.SaborPastel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SaborPastelRepository(private val saborPastelDAO: SaborPastelDAO) {
    suspend fun insert(saborPastel: SaborPastel) = saborPastelDAO.insert(saborPastel.toEntity());

    suspend fun update(saborPastel: SaborPastel) = saborPastelDAO.update(saborPastel.toEntity())

    suspend fun delete(saborPastel: SaborPastel) = saborPastelDAO.delete(saborPastel.toEntity())

    fun getAll(): Flow<List<SaborPastel>> =
        saborPastelDAO.getAll().map { it.map { it.toDomain() } }
}