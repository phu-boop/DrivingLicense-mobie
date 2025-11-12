package com.example.drivinglicence.app.activites

import android.annotation.SuppressLint
import android.graphics.Color
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.component.activity.BaseCoreActivity
import com.example.drivinglicence.databinding.ActivitySearchLawBinding
import com.example.drivinglicence.utils.URL_LAW

class SearchLawActivity : BaseCoreActivity<ActivitySearchLawBinding>() {
    override fun initView() {
        supportActionBar?.hide()
        setupToolBar()
    }

    private fun setupToolBar() {
        binding.toolbar.setTitle(getString(R.string.text_search_law))
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.toolbar.setTitleColor(Color.WHITE)
        binding.toolbar.setIconLeft(R.drawable.icon_back_white)
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        loadingDialog.show(this,"")
        loadingDialog.setMessage("Loading...")
        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingDialog.dismiss()
            }
        }
        binding.webView.clearCache(true)
        binding.webView.clearHistory()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webView.loadUrl(URL_LAW)
    }
}