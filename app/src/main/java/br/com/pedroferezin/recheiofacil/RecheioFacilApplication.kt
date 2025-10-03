package br.com.pedroferezin.recheiofacil

import android.app.Application
import br.com.pedroferezin.recheiofacil.data.SaborPastelDatabase
import br.com.pedroferezin.recheiofacil.repository.SaborPastelRepository

class RecheioFacilApplication : Application() {
    private val database by lazy { SaborPastelDatabase.getDatabase(this) }
    val repository by lazy { SaborPastelRepository(database.saborPastelDAO()) }
}