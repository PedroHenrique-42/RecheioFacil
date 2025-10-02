package br.com.pedroferezin.recheiofacil.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SaborPastelEntity::class], version = 1)
abstract class SaborPastelDatabase : RoomDatabase() {
    abstract fun saborPastelDAO(): SaborPastelDAO

    companion object {
        @Volatile
        private var INSTANCE: SaborPastelDatabase? = null

        fun getDatabase(context: Context): SaborPastelDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SaborPastelDatabase::class.java,
                    "recheiofacil.db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}