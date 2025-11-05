package com.example.drivinglicence.app.fragment

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.drivinglicence.app.adapter.AnswerResultAdapter
import com.example.drivinglicence.app.entity.Answer
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.app.viewmodel.DataViewModel
import com.example.drivinglicence.app.viewmodel.MapDataViewModel
import com.example.drivinglicence.component.fragment.BaseFragment
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.FragmentLessonBinding
import com.example.drivinglicence.utils.QUESTION

class LessonResultFragment :
    BaseFragment<FragmentLessonBinding, DataViewModel>() {

    private val shareViewModel: MapDataViewModel by viewModels(ownerProducer = { requireActivity() })

    private val answerResultAdapter by lazy {
        AnswerResultAdapter()
    }

    override fun initView() {
        binding.layoutExplainAnswer.visibility = View.VISIBLE
        initData()
        RecyclerUtils.setGridManager(this, binding.rcvAnswers, answerResultAdapter)
    }

    override fun initListener() {
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val question: Question? = arguments?.getParcelable(QUESTION)
        val listAnswer: MutableList<Answer>? = question?.let { shareViewModel.getAnswer(it) }

        listAnswer?.map { answer ->
            if (answer.isCorrect) {
                binding.textExplainAnswer.text = answer.answerExplain
            }
        }
        question?.let {
            binding.textQuestionContent.text = it.content
            it.image?.let { source ->
                binding.imageQuestion.visibility = View.VISIBLE
                binding.imageQuestion.setImageResource(source)
            }
        }
        answerResultAdapter.addData(listAnswer ?: mutableListOf())


    }

    override fun onSingleClick(v: View) {
    }

}