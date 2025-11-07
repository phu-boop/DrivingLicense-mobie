package com.example.drivinglicence.component.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.multidex.BuildConfig
import androidx.viewbinding.ViewBinding
import com.example.drivinglicence.component.navigator.getCurrentFragment
import com.example.drivinglicence.component.navigator.popFragment
import com.example.drivinglicence.component.widgets.loading.LoadingDialog
import com.example.drivinglicence.pref.BindingReflex
import com.example.drivinglicence.pref.hideKeyboard
import com.mobile.mbccs.base.component.activity.ActivityManager
import com.mobile.mbccs.base.component.activity.ActivityResultObservable
import com.mobile.mbccs.base.component.activity.ActivityResultObserver

abstract class BaseCoreActivity<VB : ViewBinding> : AppCompatActivity(), View.OnClickListener,
    ActivityResultObservable {
    private val activityObserverList by lazy { mutableListOf<ActivityResultObserver>() }
    private var lastClickTime = 0L
    private var interval = 500L
    protected val loadingDialog by lazy { LoadingDialog() }
    protected val binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launchWhenResumed {
            ActivityManager.addActivity(this@BaseCoreActivity)
            initView()
            initListener()
            initData()
            if (BuildConfig.DEBUG) {
                println("SCREEN_APP ${this@BaseCoreActivity::class.java.name}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityObserverList.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }

    override fun addObserver(activityResultObserver: ActivityResultObserver) {
        activityObserverList.add(activityResultObserver)
    }

    override fun removeObserver(activityResultObserver: ActivityResultObserver) {
        activityObserverList.remove(activityResultObserver)
    }

    open fun idFragmentContainer(): Int = 0

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    override fun onClick(v: View) {
        val nowTime = System.currentTimeMillis()
        if (nowTime - lastClickTime > interval) {
            onSingleClick(v)
            lastClickTime = nowTime
        }
    }

    open fun onSingleClick(v: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.removeActivity(this)
        hideKeyboard()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            getCurrentFragment().let { fragment ->
                if (fragment.childFragmentManager.backStackEntryCount > 0) {
                    fragment.onBackPressed()
                } else {
                    popFragment(fragment)
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    protected fun <T> MutableLiveData<T>.observe(function: (T) -> Unit) {
        this.observe(this@BaseCoreActivity) {
            function.invoke(it)
        }
    }
}