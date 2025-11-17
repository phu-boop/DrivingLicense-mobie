package com.example.drivinglicence

import android.app.Application
import androidx.multidex.MultiDex

class DrivingLicenseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}