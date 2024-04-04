package com.codeboy.randomuserandroid
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi

@HiltAndroidApp
class RandomUserApp : Application() {

    val TAG = "RandomUser"
    companion object {
    }

    override fun onCreate() {
        super.onCreate()

    }
}