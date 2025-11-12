package com.example.drivinglicence.app.activites
import android.os.Handler
import android.os.Looper
import com.example.drivinglicence.app.viewmodel.DataViewModel
import com.example.drivinglicence.component.activity.BaseVMActivity
import com.example.drivinglicence.component.navigator.openActivity
import com.example.drivinglicence.databinding.SplashBinding

class SplashActivity : BaseVMActivity<SplashBinding, DataViewModel>() {
    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
        Handler(Looper.getMainLooper()).postDelayed({
            openActivity(HomeActivity::class.java)
            finish()
        }, 1500)
    }
}