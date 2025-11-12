package com.example.drivinglicence.app.activites

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.ViewPagerAdapter
import com.example.drivinglicence.app.entity.Answer
import com.example.drivinglicence.app.entity.ListAnswers
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.app.fragment.LessonCountDownFragment
import com.example.drivinglicence.app.fragment.LessonResultFragment
import com.example.drivinglicence.app.viewmodel.MapDataViewModel
import com.example.drivinglicence.component.activity.BaseVMActivity
import com.example.drivinglicence.component.dialog.AlertMessageDialog
import com.example.drivinglicence.component.dialog.ListQuestionCountDownBottomDialog
import com.example.drivinglicence.databinding.ActivityLessonViewPagerBinding
import com.example.drivinglicence.utils.*
import kotlin.math.roundToInt

class CountDownTestActivity : BaseVMActivity<ActivityLessonViewPagerBinding, MapDataViewModel>() {

    private val viewpagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager)
    }
    private var mListFragment: MutableList<Fragment> = mutableListOf()

    var timeCount: Long = 0
    var isFinish = false
    var timer = object : CountDownTimer(1000 * 20 * 60, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val ms = millisUntilFinished / 1000
            timeCount = ms
            binding.toolbar.setTitle(formatTime(ms))
        }

        override fun onFinish() {
            //end test exam
            isFinish = true
            handlerFinish()
        }
    }

    private fun formatTime(time: Long): String {
        val minutes: Int = (time / 60).toDouble().roundToInt()
        val second = time - minutes * 60
        return "$minutes : $second"
    }


    override fun initView() {
        binding.toolbar.setIconLeft(R.drawable.icon_back_white)
        binding.toolbar.setAction(getString(R.string.text_end))
        binding.toolbar.setActionColor(Color.WHITE)
        timer.start()
        mListFragment.clear()
        viewModel.listQuestion.clear()
        viewModel.listAnswer.clear()
    }


    override fun initListener() {
        binding.toolbar.onLeftClickListener = {
            onBackPressed()
            timer.cancel()
        }
        binding.toolbar.onActionClickListener = {
            showDialogFinish()
        }
        binding.btnBackQuestion.setOnClickListener(this)
        binding.btnForwardQuestion.setOnClickListener(this)
        binding.btnViewBottom.setOnClickListener(this)
        binding.layoutShowBottom.setOnClickListener(this)
        binding.btnFinish.setOnClickListener(this)
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
                        binding.btnFinish.visibility = View.GONE
                    }
                    mListFragment.size - 1 -> {
                        binding.btnBackQuestion.visibility = View.VISIBLE
                        binding.btnForwardQuestion.visibility = View.GONE
                        if (!isFinish)
                            binding.btnFinish.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.btnBackQuestion.visibility = View.VISIBLE
                        binding.btnForwardQuestion.visibility = View.VISIBLE
                        binding.btnFinish.visibility = View.GONE
                    }
                }
                binding.textCurrentQuestion.text = "${position + 1}"
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun showDialogFinish() {
        var notChoose = listQuestion.size
        listQuestion.map { item -> if (item.isChooseAnswer == true) notChoose-- }
        AlertMessageDialog(this).also { dialog ->
            timer.cancel()
            dialog.setIconImageAlert(R.drawable.alert_warning)
            dialog.setBackgroundButtonSubmit(R.drawable.round_button_yellow_light)
            dialog.show(
                getString(R.string.text_confirm_exit),
                getString(R.string.text_time_remaining) + ": ${formatTime(timeCount)},\n"
                        + getString(R.string.text_question_not_choose) + ": $notChoose",
                onClickSubmit = {
                    isFinish = true
                    timer.cancel()
                    handlerFinish()
                },
                onClickCancel = {
                    setTimer(timeCount)
                },
                cancelAble = false
            )
        }
    }

    private fun setTimer(time: Long) {
        timer = object : CountDownTimer(time * 1000, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val ms = millisUntilFinished / 1000
                timeCount = ms
                binding.toolbar.setTitle(formatTime(ms))
            }

            override fun onFinish() {
                //end test exam
                isFinish = true
                handlerFinish()
            }
        }
        timer.start()
    }

    private fun handlerFinish() {
        /**Thống kê câu sai và đúng, kiểm tra trượt hay đỗ*/
        val listQuestionCorrect = mutableListOf<Int>()
        val listQuestionWrong = mutableListOf<Int>()
        var isPass = true
        var count = 0
        viewModel.mapResult.map { entry ->
            if (entry.value) {
                count++
                for (i in 0 until listQuestion.size) {
                    if (listQuestion[i].questionId == entry.key) {
                        listQuestionCorrect.add(i + 1)
                        break
                    }
                }
            } else {
                if (entry.key <= 35) {
                    isPass = false
                }
            }
        }
        var msgCorrect = "$count"
        for (i in 1..listQuestion.size) {
            if (!listQuestionCorrect.contains(i)) {
                listQuestionWrong.add(i)
            }
        }
        var msgInCorrect = ""
        for (item in 0 until listQuestionWrong.size) {
            msgInCorrect += if (item != listQuestionWrong.size - 1) {
                "${listQuestionWrong[item]}, "
            } else {
                "${listQuestionWrong[item]}"
            }
        }
        //count < 21
        if (count < 27) {
            isPass = false
        }
        if ((intent.extras?.getInt("number_test")?:0) == 6){
            isPass = true
        }
        AlertMessageDialog(this).also { alert ->
            val buttonText = getString(R.string.text_confirm)
            val title: String
            when (isPass) {
                true -> { // pass
                    title = getString(R.string.text_pass_exam)
                    alert.setColorTitle(ContextCompat.getColor(this, R.color.green))
                    alert.setIconImageAlert(R.drawable.success)
                }
                else -> { // failed
                    title = getString(R.string.text_failed_exam)
                    alert.setColorTitle(ContextCompat.getColor(this, R.color.red))
                    alert.setIconImageAlert(R.drawable.failed)
                }
            }

            alert.show(title,
                getString(R.string.text_quantity_answer_correct) + ": $msgCorrect\n"
                        + getString(R.string.text_quantity_answer_incorrect) + ": $msgInCorrect",
                buttonText,
                cancelAble = false,
                onClickSubmit = {
                    alert.hide()
                    /**Update Result to view pager*/
                    showResult()
//                    onBackPressed()
                }
            )
            alert.hideCancelButton()
        }
    }

    private fun showResult() {
        viewModel.listQuestion
        viewModel.listAnswer
        mListFragment.clear()
        viewpagerAdapter.clearFragment()
        binding.toolbar.setTitle("0 : 0")
        binding.toolbar.hideAction()
        for (i in 0 until viewModel.listAnswer.size) {
            val question = viewModel.listQuestion[i]
            val answers = viewModel.listAnswer[i] as ArrayList<Answer>
            mListFragment.add(LessonResultFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(QUESTION, question)
                bundle.putParcelableArrayList(ANSWERS, answers)
                arguments = bundle
            })
        }
        viewpagerAdapter.addFragment(mListFragment)
        viewpagerAdapter.notifyDataSetChanged()
        binding.btnFinish.visibility = View.GONE
    }

    private var listQuestion: MutableList<Question> = mutableListOf()
    private var listAnswer: MutableList<MutableList<Answer>> = mutableListOf()
    override fun initData() {
        viewModel.getAllData(this)
        listQuestion = intent.extras?.getParcelableArrayList(QUESTIONS) ?: mutableListOf()
        listQuestion.map {
            listAnswer.add(viewModel.mapping[it.questionId] ?: mutableListOf())
        }
        viewModel.listQuestion = listQuestion
        viewModel.listAnswer = listAnswer
        for (i in 1..listAnswer.size) {
            val question = listQuestion[i - 1]
            val answers = listAnswer[i - 1] as ArrayList<Answer>
            mListFragment.add(LessonCountDownFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(QUESTION, question)
                bundle.putParcelableArrayList(ANSWERS, answers)
                arguments = bundle
            })
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
            R.id.btnFinish -> {
                showDialogFinish()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showBottomDialog() {
        val bundle = Bundle()
        bundle.putParcelableArrayList(QUESTIONS, viewModel.listQuestion as ArrayList)
        val map = mutableMapOf<Int, Boolean>()
        viewModel.listQuestion.map {
            map[it.questionId] = it.isChooseAnswer ?: false
        }
        bundle.putInt(POSITION, binding.textCurrentQuestion.text.toString().toInt() - 1)
        ListQuestionCountDownBottomDialog().also { dialog ->
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "")
            dialog.onClickItem = { pos ->
                dialog.dismiss()
                when (pos) {
                    0 -> {
                        binding.btnBackQuestion.visibility = View.GONE
                        binding.btnFinish.visibility = View.GONE
                    }
                    mListFragment.size - 1 -> {
                        binding.btnBackQuestion.visibility = View.VISIBLE
                        binding.btnForwardQuestion.visibility = View.GONE
                        if (!isFinish)
                            binding.btnFinish.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.btnBackQuestion.visibility = View.VISIBLE
                        binding.btnForwardQuestion.visibility = View.VISIBLE
                        binding.btnFinish.visibility = View.GONE
                    }
                }
                binding.textCurrentQuestion.text = "${pos + 1}"
                binding.viewPager.currentItem = pos
                viewpagerAdapter.setFragment(
                    pos,
                    LessonCountDownFragment(),
                    viewModel.listQuestion[pos]
                )
            }
        }
    }

    override fun onBackPressed() {
        if (timeCount == 0L || isFinish) {
            viewModel.listQuestion.clear()
            viewModel.listAnswer.clear()
            super.onBackPressed()
        } else {
            AlertMessageDialog(this).also { alert ->
                alert.setIconImageAlert(R.drawable.alert_warning)
                alert.show(getString(R.string.text_confirm_exit),
                    getString(R.string.text_time_remaining) + ": ${formatTime(timeCount)}",
                    onClickSubmit = {
                        viewModel.listQuestion.clear()
                        viewModel.listAnswer.clear()
                        super.onBackPressed()
                    })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearSection()
    }
}