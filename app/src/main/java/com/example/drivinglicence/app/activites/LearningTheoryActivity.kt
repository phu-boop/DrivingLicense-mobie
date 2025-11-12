package com.example.drivinglicence.app.activites

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.LearningAdapter
import com.example.drivinglicence.app.entity.ItemLearning
import com.example.drivinglicence.component.activity.BaseCoreActivity
import com.example.drivinglicence.component.navigator.openActivity
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.ActivityLearningTheoryBinding
import com.example.drivinglicence.utils.FLAG

class LearningTheoryActivity : BaseCoreActivity<ActivityLearningTheoryBinding>() {
    private val learningAdapter by lazy {
        LearningAdapter()
    }

    override fun initView() {
        supportActionBar?.hide()
        setupToolBar()
        RecyclerUtils.setGridManager(this, binding.rcvLearning, learningAdapter)
    }

    private fun setupToolBar() {
        binding.toolbar.setTitle(getString(R.string.text_learning_theory))
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.toolbar.setTitleColor(Color.WHITE)
        binding.toolbar.setIconLeft(R.drawable.icon_back_white)
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
        learningAdapter.setOnClickItemRecyclerView { _, position ->
            val bundle = Bundle()
            when (position) {
                0 -> {
                    /**35 câu hỏi điểm liệt*/
                    bundle.putInt(FLAG,1)
                    openActivity(LessonViewPagerActivity::class.java, bundle)
                }
                1 -> {
                    /**Khái niệm và quy tắc*/
                    bundle.putInt(FLAG,2)
                    openActivity(LessonViewPagerActivity::class.java, bundle)
                }
                2 -> {
                    /**Văn hóa và đạo đức lái xe*/
                    bundle.putInt(FLAG,3)
                    openActivity(LessonViewPagerActivity::class.java, bundle)
                }
                3 -> {
                    /**Kĩ thuật lái xe*/
                    bundle.putInt(FLAG,4)
                    openActivity(LessonViewPagerActivity::class.java, bundle)
                }
                4 -> {
                    /**Biển báo đường bộ*/
                    bundle.putInt(FLAG,5)
                    openActivity(LessonViewPagerActivity::class.java, bundle)
                }
                5 -> {
                    /**Sa hình*/
                    bundle.putInt(FLAG,6)
                    openActivity(LessonViewPagerActivity::class.java, bundle)
                }
            }
        }
    }

    override fun initData() {
        val list = arrayListOf(
            ItemLearning(
                getString(R.string.text_35_question_important),
                getString(R.string.text_35_question_important),
                R.drawable.warning
            ),
            ItemLearning(
                getString(R.string.text_concepts_and_rules),
                getString(R.string.text_about_30),
                R.drawable.writing
            ),
            ItemLearning(
                getString(R.string.text_culture_and_ethic),
                getString(R.string.text_about_5),
                R.drawable.cultures
            ),
            ItemLearning(
                getString(R.string.text_driving_technique), getString(R.string.text_about_12),
                R.drawable.driving
            ),
            ItemLearning(
                getString(R.string.text_road_signs),
                getString(R.string.text_about_20),
                R.drawable.forbidden
            ),
            ItemLearning(
                getString(R.string.text_sat_figure),
                getString(R.string.text_about_20),
                R.drawable.conversation
            )
        )
        learningAdapter.addData(list)
    }

    override fun onSingleClick(v: View) {
        when (v.id) {

        }
    }
}