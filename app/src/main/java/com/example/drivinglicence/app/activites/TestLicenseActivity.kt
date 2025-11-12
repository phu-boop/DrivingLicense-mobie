package com.example.drivinglicence.app.activites

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.ExamAdapter
import com.example.drivinglicence.app.entity.Answer
import com.example.drivinglicence.app.entity.ListAnswers
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.app.viewmodel.MapDataViewModel
import com.example.drivinglicence.component.activity.BaseVMActivity
import com.example.drivinglicence.component.dialog.AlertMessageDialog
import com.example.drivinglicence.component.dialog.InformationLessonBottomSheet
import com.example.drivinglicence.component.navigator.openActivity
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.ActivityTestLicenseBinding
import com.example.drivinglicence.pref.LocalCache
import com.example.drivinglicence.utils.IS_SECOND
import com.example.drivinglicence.utils.LIST_ANSWERS
import com.example.drivinglicence.utils.QUESTIONS

class TestLicenseActivity : BaseVMActivity<ActivityTestLicenseBinding, MapDataViewModel>() {
    private val examAdapter by lazy {
        ExamAdapter()
    }
    private var listQuestion: MutableList<Question> = mutableListOf()
    private var listAnswer: MutableList<MutableList<Answer>> = mutableListOf()
    private var isFirstClickExam = true
    override fun initView() {
        setupToolBar()
        RecyclerUtils.setGridManager(this, binding.rcvExams, examAdapter)
        try {
            val isSecond = LocalCache.getInstance().getBoolean(IS_SECOND) ?: false
            if (!isSecond) {
                showAlertMessage()
            }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    private fun setupToolBar() {
        binding.toolbar.setTitle(getString(R.string.text_exam))
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.toolbar.setTitleColor(Color.WHITE)
        binding.toolbar.setIconLeft(R.drawable.icon_back_white)
        binding.toolbar.setIconRight(R.drawable.icon_info)
    }
    private var numberOfTest = 0
    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
        binding.toolbar.onRightClickListener = {
            showAlertMessage()
        }
        examAdapter.setOnClickItemRecyclerView { position, _ ->
            numberOfTest = position
            listQuestion = viewModel.getQuestionTest(position)
//            listQuestion = listQuestion.shuffled() as MutableList<Question>
            listAnswer = viewModel.getAnswerTest(listQuestion)
            showInformationLicense()
        }
        binding.btnCreateExam.setOnClickListener(this)
    }

    private fun showInformationLicense() {
        if (isFirstClickExam) {
            InformationLessonBottomSheet().also { dialog ->
                dialog.show(supportFragmentManager, "")
                dialog.startTestClickListener = {
                    openCountDown()
                    isFirstClickExam = false
                }
            }
        } else {
            openCountDown()
        }
    }

    private fun openCountDown() {
        val bundle = Bundle()
        listAnswer.map {
            it.map { it1 ->
                it1.flag = 1
            }
        }
        bundle.putParcelableArrayList(QUESTIONS, listQuestion as ArrayList)
        bundle.putParcelable(LIST_ANSWERS, ListAnswers(listAnswer))
        bundle.putInt("number_test", numberOfTest)
        openActivity(CountDownTestActivity::class.java, bundle)
    }

    override fun initData() {
        for (i in 1..6) {
            examAdapter.addData(i)
        }
        loadingDialog.show(this, "")
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            loadingDialog.dismiss()
        }, 1500)
    }

    private fun showAlertMessage() {
        AlertMessageDialog(this).also { alert ->
            alert.setIconImageAlert(R.drawable.information)
            alert.show(
                getString(R.string.text_tutorial_exam), getString(R.string.text_information_exam),
                getString(R.string.text_confirm),
                cancelAble = false,
                onClickSubmit = {
                    putIsFirst()
                }
            )
            alert.hideCancelButton()
        }
    }

    private fun putIsFirst() {
        LocalCache.getInstance().put(IS_SECOND, true)
    }

    override fun onSingleClick(v: View) {
        when (v.id) {
            R.id.btn_create_exam -> {
                loadingDialog.show(this, "")
                loadingDialog.setMessage(getString(R.string.text_creating_random_exam))
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    examAdapter.addData(examAdapter.dataList.size + 1)
                    loadingDialog.dismiss()
                }, 700)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllData(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearSection()
    }
}