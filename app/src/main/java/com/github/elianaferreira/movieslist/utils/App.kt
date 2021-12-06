package com.github.elianaferreira.movieslist.utils

import android.app.Application

class App: Application() {

    private var apiKey: String? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getApiKey(): String? {
        return this.apiKey
    }

    fun setApiKey(apiKey: String) {
        this.apiKey = apiKey
    }

    companion object {
        lateinit var instance: App
            private set
    }
}