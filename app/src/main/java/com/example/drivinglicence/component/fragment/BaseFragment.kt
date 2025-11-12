package com.example.drivinglicence.component.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.multidex.BuildConfig
import androidx.viewbinding.ViewBinding

import com.example.drivinglicence.component.viewmodel.BaseViewModel
import com.example.drivinglicence.component.widgets.loading.LoadingDialog
import com.example.drivinglicence.pref.BindingReflex
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(),
    View.OnClickListener, DefaultLifecycleObserver {

    private val loadingDialog by lazy { LoadingDialog() }
    private var isLoaded = false
    private var lastClickTime = 0L
    private val interval by lazy { 500L }
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    private fun <KClass> genericViewModel(): Class<KClass> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<KClass>
    }

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[genericViewModel()]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == _binding) {
            _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
            initView()
            initListener()
        }
        return binding.root.apply {
            isFocusable = true
            isClickable = true
        }
    }

    override fun onResume() {
        super<Fragment>.onResume()
        if (!isLoaded && !isHidden) {
            lifecycleScope.launchWhenResumed {
                handleLoading()
                initData()
                if (BuildConfig.DEBUG) {
                    println("SCREEN_APP ${this@BaseFragment::class.java.name}")
                }
            }
            isLoaded = true
        }
    }

    open fun handleLoading() {
        try {
            viewModel.isLoading.observe { isShow ->
                if (isShow) {
                    Handler(Looper.getMainLooper()).post {
                        if (isVisible && !loadingDialog.isAdded) {
                            loadingDialog.show(this)
                        }
                    }
                } else {
                    if (isVisible) {
                        loadingDialog.dismissAllowingStateLoss()
                    }
                }
            }
        } catch (e: Exception) {
        }
    }
    open fun idFragmentContainer(): Int = 0

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    override fun onDestroy() {
        isLoaded = false
        super<Fragment>.onDestroy()
    }

    override fun onDetach() {
        _binding = null
        lifecycle.removeObserver(this)
        super.onDetach()
    }

    override fun onClick(v: View) {
        val nowTime = System.currentTimeMillis()
        if (nowTime - lastClickTime > interval) {
            onSingleClick(v)
            lastClickTime = nowTime
        }
    }

    open fun onSingleClick(v: View) {

    }

    open fun onBackPressed() {
        if (!childFragmentManager.popBackStackImmediate()) {
            requireActivity().onBackPressed()
        }
    }

    private fun hasBackStack(): Boolean {
        return childFragmentManager.backStackEntryCount > 1
    }

    protected fun <T> MutableLiveData<T>.observe(function: (T) -> Unit) {
        this.observe(this@BaseFragment) {
            function.invoke(it)
        }
    }
}