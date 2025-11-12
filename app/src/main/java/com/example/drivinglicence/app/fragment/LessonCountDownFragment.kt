package com.example.drivinglicence.app.fragment

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.drivinglicence.app.adapter.AnswerCountDownAdapter
import com.example.drivinglicence.app.entity.Answer
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.app.viewmodel.DataViewModel
import com.example.drivinglicence.app.viewmodel.MapDataViewModel
import com.example.drivinglicence.component.fragment.BaseFragment
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.FragmentLessonBinding
import com.example.drivinglicence.utils.ANSWERS
import com.example.drivinglicence.utils.QUESTION

class LessonCountDownFragment :
    BaseFragment<FragmentLessonBinding, DataViewModel>() {

    private val shareViewModel: MapDataViewModel by viewModels(ownerProducer = { requireActivity() })

    private val answerCountDownAdapter by lazy {
        AnswerCountDownAdapter()
    }

    override fun initView() {
        initData()
        RecyclerUtils.setGridManager(this, binding.rcvAnswers, answerCountDownAdapter)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initListener() {
        answerCountDownAdapter.setOnClickItemRecyclerView { answer, position ->
            /**set question is selected answer*/
            shareViewModel.listQuestion.map { question ->
                if (question.questionId == answer.questionId) {
                    //biến để check xem question đã được chọn câu hỏi chưa, đã chọn => thay layout ở bottom sheet
                    question.isChooseAnswer = true
                }
            }
            for(item in shareViewModel.listAnswer){
                for(i in item){
                    if (i.answerId == answer.answerId && i.questionId == answer.questionId){
                        i.isChoose = true
                    }
                }
            }
            if (answer.isCorrect) {
                shareViewModel.mapResult[answer.questionId] = true
                answer.isChoose = true
            } else {
                shareViewModel.mapResult[answer.questionId] = false
            }
            answerCountDownAdapter.dataList.map { it.flag = 1 }
            answer.flag = 2
            answerCountDownAdapter.notifyDataSetChanged()
            Log.d(
                "TAG",
                "Chọn câu ${position + 1}, answer.iscorrect = ${answer.isCorrect}, map = ${shareViewModel.mapResult} "
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val question: Question? = arguments?.getParcelable(QUESTION)
        var listAnswer: MutableList<Answer>? = question?.let { shareViewModel.getAnswer(it) }
        question?.let {
            binding.textQuestionContent.text = it.content
            it.image?.let { source ->
                binding.imageQuestion.visibility = View.VISIBLE
                binding.imageQuestion.setImageResource(source)
            }
        }
        answerCountDownAdapter.addData(listAnswer ?: mutableListOf())
    }

    override fun onSingleClick(v: View) {
    }

}