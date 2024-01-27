package com.example.a2048app.app

import android.app.Application
import com.example.a2048app.domain.AppRepositoryImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppRepositoryImpl.getInstance(this)
    }

}