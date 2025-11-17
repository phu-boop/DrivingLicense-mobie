package com.example.drivinglicence.app.adapter

import android.app.Application
import com.tencent.mmkv.MMKV
import com.example.drivinglicence.utils.FcmUtils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Khởi tạo MMKV
        MMKV.initialize(this)

        // Khởi tạo FCM
        FcmUtils.initializeFcm(this)
    }
}
