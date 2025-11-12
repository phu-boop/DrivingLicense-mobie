package com.example.drivinglicence.component.widgets.loading

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.drivinglicence.component.dialog.BaseDialogFragment
import com.example.drivinglicence.databinding.LoadingDialogBinding

class LoadingDialog : BaseDialogFragment<LoadingDialogBinding>() {

    override fun getDialogStyle(): Int {
        return STYLE_NO_FRAME
    }

    override fun getDialogTheme(): Int {
//        return R.style.fly_uikit_TransparentDialog
        return 0
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun isFullScreen(): Boolean {
        return true
    }

    override fun show(activity: FragmentActivity, tag: String?) {
        super.show(activity, tag)
        binding?.loadingView?.start()
    }

    override fun show(fragment: Fragment, tag: String?) {
        super.show(fragment, tag)
        binding?.loadingView?.start()
    }

    override fun dismiss() {
        binding?.loadingView?.stop()
        super.dismiss()
    }

    override fun dismissAllowingStateLoss() {
        if (binding != null) {
            binding?.loadingView?.stop()
        }
        super.dismissAllowingStateLoss()
    }

    fun setMessage(msg: String) {
        binding?.textMessage?.text = msg
    }
}