package com.example.drivinglicence.app.activites

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.TipsAdapter
import com.example.drivinglicence.app.entity.ItemLearning
import com.example.drivinglicence.component.activity.BaseCoreActivity
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.ActivityTipsBinding

class TipsActivity : BaseCoreActivity<ActivityTipsBinding>() {
    private val tipsAdapter by lazy {
        TipsAdapter()
    }

    override fun initView() {
        supportActionBar?.hide()
        setupToolBar()
        RecyclerUtils.setGridManager(this, binding.rcvTips, tipsAdapter)
    }

    private fun setupToolBar() {
        binding.toolbar.setTitle(getString(R.string.text_tips))
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.toolbar.setTitleColor(Color.WHITE)
        binding.toolbar.setIconLeft(R.drawable.icon_back_white)
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
    }

    override fun initData() {
        val list = mutableListOf(
            ItemLearning(getString(R.string.text_tip_1), getString(R.string.text_tip_content_1), 0),
            ItemLearning(getString(R.string.text_tip_2), getString(R.string.text_tip_content_2), 0),
            ItemLearning(getString(R.string.text_tip_3), getString(R.string.text_tip_content_3), 0),
            ItemLearning(getString(R.string.text_tip_4), getString(R.string.text_tip_content_4), 0),
            ItemLearning(getString(R.string.text_tip_5), getString(R.string.text_tip_content_5), 0),
            ItemLearning(getString(R.string.text_tip_6), getString(R.string.text_tip_content_6), 0),
            ItemLearning(getString(R.string.text_tip_7), getString(R.string.text_tip_content_7), 0),
            ItemLearning(getString(R.string.text_tip_8), getString(R.string.text_tip_content_8), 0),
            ItemLearning(getString(R.string.text_tip_9), getString(R.string.text_tip_content_9), 0),
            ItemLearning(
                getString(R.string.text_tip_10), getString(R.string.text_tip_content_10),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_11), getString(R.string.text_tip_content_11),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_12), getString(R.string.text_tip_content_12),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_13), getString(R.string.text_tip_content_13),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_14), getString(R.string.text_tip_content_14),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_15), getString(R.string.text_tip_content_15),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_16), getString(R.string.text_tip_content_16),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_17), getString(R.string.text_tip_content_17),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_18), getString(R.string.text_tip_content_18),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_20), getString(R.string.text_tip_content_20),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_21), getString(R.string.text_tip_content_21),
                0
            ),
            ItemLearning(
                getString(R.string.text_tip_22), getString(R.string.text_tip_content_22),
                0
            ),
        )
        tipsAdapter.dataList = list
    }
}