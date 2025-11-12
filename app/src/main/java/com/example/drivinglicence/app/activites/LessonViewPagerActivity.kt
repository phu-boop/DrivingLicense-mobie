package com.example.drivinglicence.app.activites

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.ViewPagerAdapter
import com.example.drivinglicence.app.entity.Answer
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.app.fragment.LessonFragment
import com.example.drivinglicence.app.viewmodel.MapDataViewModel
import com.example.drivinglicence.component.activity.BaseVMActivity
import com.example.drivinglicence.component.dialog.ListQuestionBottomDialog
import com.example.drivinglicence.databinding.ActivityLessonViewPagerBinding
import com.example.drivinglicence.utils.*

class LessonViewPagerActivity : BaseVMActivity<ActivityLessonViewPagerBinding, MapDataViewModel>() {
    private val viewpagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager)
    }
    private val mListFragment: MutableList<Fragment> = mutableListOf()

    override fun initView() {
        binding.toolbar.setIconLeft(R.drawable.icon_back_white)
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
        binding.btnBackQuestion.setOnClickListener(this)
        binding.btnForwardQuestion.setOnClickListener(this)
        binding.btnViewBottom.setOnClickListener(this)
        binding.layoutShowBottom.setOnClickListener(this)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.btnBackQuestion.visibility = View.GONE
                    }
                    mListFragment.size - 1 -> {
                        binding.btnBackQuestion.visibility = View.VISIBLE
                        binding.btnForwardQuestion.visibility = View.GONE
                    }
                    else -> {
                        binding.btnBackQuestion.visibility = View.VISIBLE
                        binding.btnForwardQuestion.visibility = View.VISIBLE
                    }
                }
                binding.textCurrentQuestion.text = "${position + 1}"
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private var listQuestion: MutableList<Question> = mutableListOf()
    private var listAnswer: MutableList<MutableList<Answer>> = mutableListOf()
    override fun initData() {
        /**
         * 1: 35 Câu hỏi điểm liệt
         * 2: 30 Câu hỏi khái niệm và quy tắc
         * 3: 5 câu hỏi văn hóa và đạo đức lái xe
         * 4: 12 câu hỏi kĩ thuật lái xe
         * 5: 20 câu hỏi biển báo đường bộ
         * 6: 20 câu hỏi sa hình
         * */
        //danh sach cau tra loi cua 3 cau hoi
        when (intent.extras?.getInt(FLAG) ?: 1) {
            1 -> {
                binding.toolbar.setTitle(getString(R.string.text_35_question_important))
                /**35 Câu hỏi điểm liệt*/
                listQuestion = viewModel.getListQuestionImportant(this)
                for (i in 1..35) {
                    listAnswer.add(viewModel.mapAnswerImportant[i] ?: mutableListOf())
                }
                for (i in 1..35) {
                    val question = listQuestion[i - 1]
                    val answers = listAnswer[i - 1] as ArrayList<Answer>
                    mListFragment.add(LessonFragment().apply {
                        val bundle = Bundle()
                        bundle.putParcelable(QUESTION, question)
                        bundle.putParcelableArrayList(ANSWERS, answers)
                        arguments = bundle
                    })
                }
            }
            2 -> {
                binding.toolbar.setTitle(getString(R.string.text_concepts_and_rules))
                /**30 Câu hỏi khái niệm và quy tắc*/
                listQuestion = viewModel.getListQuestionConceptsAndRules(this)
                listQuestion = listQuestion.shuffled() as MutableList<Question>
                for (i in 1..30) {
                    listAnswer.add(viewModel.mapAnswerConceptsAndRules[i + 40] ?: mutableListOf())
                }
                for (i in 1..30) {
                    val question = listQuestion[i - 1]
                    val answers =
                        viewModel.mapAnswerConceptsAndRules[question.questionId] as ArrayList<Answer>
                    mListFragment.add(LessonFragment().apply {
                        val bundle = Bundle()
                        bundle.putParcelable(QUESTION, question)
                        bundle.putParcelableArrayList(ANSWERS, answers)
                        arguments = bundle
                    })
                }
            }
            3 -> {
                binding.toolbar.setTitle(getString(R.string.text_culture_and_ethic))
                /**5 câu Văn hóa và đạo đức lái xe*/
                listQuestion = viewModel.getListQuestionCulturesAndEthics(this)
                for (i in 1..5) {
                    listAnswer.add(viewModel.mapAnswerCulturesAndEthics[i + 80] ?: mutableListOf())
                }
                for (i in 1..5) {
                    val question = listQuestion[i - 1]
                    val answers = listAnswer[i - 1] as ArrayList<Answer>
                    mListFragment.add(LessonFragment().apply {
                        val bundle = Bundle()
                        bundle.putParcelable(QUESTION, question)
                        bundle.putParcelableArrayList(ANSWERS, answers)
                        arguments = bundle
                    })
                }
            }
            4 -> {
                binding.toolbar.setTitle(getString(R.string.text_driving_technique))
                /**12 câu kỹ thuật lái xe*/
                listQuestion = viewModel.getListQuestionDrivingUnique(this)
                for (i in 1..12) {
                    listAnswer.add(viewModel.mapAnswerDrivingUnique[i + 90] ?: mutableListOf())
                }
                for (i in 1..12) {
                    val question = listQuestion[i - 1]
                    val answers = listAnswer[i - 1] as ArrayList<Answer>
                    mListFragment.add(LessonFragment().apply {
                        val bundle = Bundle()
                        bundle.putParcelable(QUESTION, question)
                        bundle.putParcelableArrayList(ANSWERS, answers)
                        arguments = bundle
                    })
                }
            }
            5 -> {
                binding.toolbar.setTitle(getString(R.string.text_road_signs))
                /**20 câu biển báo đường bộ*/
                listQuestion = viewModel.getListQuestionRoadSign(this)
                for (i in 1..20) {
                    listAnswer.add(viewModel.mapAnswerRoadSign[i + 110] ?: mutableListOf())
                }
                for (i in 1..20) {
                    val question = listQuestion[i - 1]
                    val answers = listAnswer[i - 1] as ArrayList<Answer>
                    mListFragment.add(LessonFragment().apply {
                        val bundle = Bundle()
                        bundle.putParcelable(QUESTION, question)
                        bundle.putParcelableArrayList(ANSWERS, answers)
                        arguments = bundle
                    })
                }
            }
            6 -> {
                binding.toolbar.setTitle(getString(R.string.text_sat_figure))
                /**20 câu sa hình*/
                listQuestion = viewModel.getListQuestionSatFigure(this)
                for (i in 1..20) {
                    listAnswer.add(viewModel.mapAnswerSatFigure[i + 140] ?: mutableListOf())
                }
                for (i in 1..20) {
                    val question = listQuestion[i - 1]
                    val answers = listAnswer[i - 1] as ArrayList<Answer>
                    mListFragment.add(LessonFragment().apply {
                        val bundle = Bundle()
                        bundle.putParcelable(QUESTION, question)
                        bundle.putParcelableArrayList(ANSWERS, answers)
                        arguments = bundle
                    })
                }
            }
        }
        viewpagerAdapter.addFragment(mListFragment)
        binding.viewPager.adapter = viewpagerAdapter
        binding.textTotalQuestion.text = viewpagerAdapter.count.toString()
    }

    override fun onSingleClick(v: View) {

        when (v.id) {
            R.id.btnBackQuestion -> {
                binding.viewPager.currentItem = binding.viewPager.currentItem - 1
            }
            R.id.btnForwardQuestion -> {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
            R.id.btn_view_bottom, R.id.layout_show_bottom -> {
                showBottomDialog()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showBottomDialog() {
        Log.d("TAG", "showBottomDialog: ")
        val bundle = Bundle()
        bundle.putParcelableArrayList(QUESTIONS, listQuestion as ArrayList)
        bundle.putInt(POSITION, binding.textCurrentQuestion.text.toString().toInt() - 1)
        ListQuestionBottomDialog().also { dialog ->
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "")
            dialog.onClickItem = { pos ->
                dialog.dismiss()
                when (pos) {
                    0 -> {
                        binding.btnBackQuestion.visibility = View.GONE
                    }
                    mListFragment.size - 1 -> {
                        binding.btnBackQuestion.visibility = View.VISIBLE
                        binding.btnForwardQuestion.visibility = View.GONE
                    }
                    else -> {
                        binding.btnBackQuestion.visibility = View.VISIBLE
                        binding.btnForwardQuestion.visibility = View.VISIBLE
                    }
                }
                binding.textCurrentQuestion.text = "${pos + 1}"
                binding.viewPager.currentItem = pos
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewpagerAdapter.clearFragment()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllData(this)
        println("SCREEN_APP resume LessonViewPagerActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewpagerAdapter.clearFragment()
        viewModel.clearSection()
        println("SCREEN_APP destroy LessonViewPagerActivity")
    }
}