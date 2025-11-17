package com.example.drivinglicence.app.activites

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
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
import com.example.drivinglicence.component.widgets.skeleton.SkeletonUtils
import com.example.drivinglicence.databinding.ActivityTestLicenseBinding
import com.example.drivinglicence.pref.LocalCache
import com.example.drivinglicence.utils.IS_SECOND
import com.example.drivinglicence.utils.LIST_ANSWERS
import com.example.drivinglicence.utils.QUESTIONS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestLicenseActivity : BaseVMActivity<ActivityTestLicenseBinding, MapDataViewModel>() {

    private val examAdapter by lazy { ExamAdapter() }
    private var listQuestion: MutableList<Question> = mutableListOf()
    private var listAnswer: MutableList<MutableList<Answer>> = mutableListOf()
    private var isFirstClickExam = true
    private var numberOfTest = 0

    // Skeleton - FIXED APPROACH
    private var isLoading = false
    private lateinit var skeletonContainer: ViewGroup
    private var skeletonView: View? = null

    override fun initView() {
        // Tạo container riêng cho skeleton
        skeletonContainer = binding.root.findViewById(R.id.skeleton_container) ?: createSkeletonContainer()

        // Hiển thị skeleton
        showSkeletonLoading()

        // Giả lập load data
        simulateDataLoading()
    }

    private fun createSkeletonContainer(): ViewGroup {
        // Tạo container mới cho skeleton nếu chưa có trong layout
        val container = ViewGroup.LayoutParams.MATCH_PARENT to ViewGroup.LayoutParams.MATCH_PARENT
        val frameLayout = android.widget.FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(container.first, container.second)
            id = R.id.skeleton_container
        }

        (binding.root as? ViewGroup)?.addView(frameLayout)
        return frameLayout
    }

    override fun initData() {
        // Chức năng thêm dữ liệu ban đầu cho adapter
        for (i in 1..6) {
            examAdapter.addData(i)
        }
    }

    override fun initListener() {
        // Toolbar
        setupToolBar()
        binding.toolbar.onLeftClickListener = { onBackPressed() }
        binding.toolbar.onRightClickListener = { showAlertMessage() }

        // Adapter item click
        examAdapter.setOnClickItemRecyclerView { position, _ ->
            numberOfTest = position
            listQuestion = viewModel.getQuestionTest(position)
            listAnswer = viewModel.getAnswerTest(listQuestion)
            showInformationLicense()
        }

        // Nút tạo đề thi
        binding.btnCreateExam.setOnClickListener(this)
    }

    private fun setupToolBar() {
        binding.toolbar.setTitle(getString(R.string.text_exam))
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.toolbar.setTitleColor(Color.WHITE)
        binding.toolbar.setIconLeft(R.drawable.icon_back_white)
        binding.toolbar.setIconRight(R.drawable.icon_info)
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
        listAnswer.forEach { ansList ->
            ansList.forEach { it.flag = 1 }
        }
        bundle.putParcelableArrayList(QUESTIONS, ArrayList(listQuestion))
        bundle.putParcelable(LIST_ANSWERS, ListAnswers(listAnswer))
        bundle.putInt("number_test", numberOfTest)
        openActivity(CountDownTestActivity::class.java, bundle)
    }

    override fun onSingleClick(v: View) {
        when (v.id) {
            R.id.btn_create_exam -> {
                loadingDialog.show(this, "")
                loadingDialog.setMessage(getString(R.string.text_creating_random_exam))
                Handler(Looper.getMainLooper()).postDelayed({
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
        // Dọn dẹp skeleton view
        skeletonView = null
    }

    private fun showAlertMessage() {
        AlertMessageDialog(this).also { alert ->
            alert.setIconImageAlert(R.drawable.information)
            alert.show(
                getString(R.string.text_tutorial_exam),
                getString(R.string.text_information_exam),
                getString(R.string.text_confirm),
                cancelAble = false,
                onClickSubmit = { putIsFirst() }
            )
            alert.hideCancelButton()
        }
    }

    private fun putIsFirst() {
        LocalCache.getInstance().put(IS_SECOND, true)
    }

    /** Skeleton loading functions - FIXED **/
    private fun showSkeletonLoading() {
        isLoading = true
        skeletonView = SkeletonUtils.showSkeletonWithAnimation(skeletonContainer, R.layout.layout_skeleton_test)

        // Ẩn content chính tạm thời
        binding.contentRoot.visibility = View.GONE // Thêm ID này trong layout của bạn
    }

    private fun hideSkeleton() {
        isLoading = false
        SkeletonUtils.hideSkeleton(skeletonContainer)

        // Hiển thị content chính
        binding.contentRoot.visibility = View.VISIBLE
        setupRealContent()
    }

    private fun simulateDataLoading() {
        lifecycleScope.launch {
            delay(2000) // giả lập 2 giây load dữ liệu
            runOnUiThread {
                hideSkeleton()
            }
        }
    }

    private fun setupRealContent() {
        // Thiết lập content thật sau khi skeleton biến mất
        RecyclerUtils.setGridManager(this, binding.rcvExams, examAdapter)
    }

    override fun onBackPressed() {
        if (isLoading) finish() else super.onBackPressed()
    }
}