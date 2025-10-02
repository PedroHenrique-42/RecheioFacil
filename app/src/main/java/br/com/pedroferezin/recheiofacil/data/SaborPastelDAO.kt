package br.com.pedroferezin.recheiofacil.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface SaborPastelDAO {
    @Insert
    suspend fun insert(saborPastelEntity: SaborPastelEntity)
}
