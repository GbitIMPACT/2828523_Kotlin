package com.example.aviacionapp

import android.app.Application
import com.example.aviacionapp.utils.SyncWorker

class AviacionApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Configurar sincronización periódica
        SyncWorker.setupPeriodicSync(this)
    }
}