package br.com.pedroferezin.recheiofacil.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SaborPastelDAO {
    @Insert
    suspend fun insert(saborPastelEntity: SaborPastelEntity)

    @Update
    suspend fun update (saborPastelEntity: SaborPastelEntity)

    @Delete
    suspend fun delete(saborPastelEntity: SaborPastelEntity)

    @Query("SELECT * FROM recheios")
    fun getAll(): Flow<List<SaborPastelEntity>>
}
