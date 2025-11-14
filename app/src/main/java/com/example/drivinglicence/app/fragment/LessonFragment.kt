package com.example.drivinglicence.app.fragment

import android.annotation.SuppressLint
import android.text.Html
import android.view.View
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.AnswerAdapter
import com.example.drivinglicence.app.entity.Answer
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.app.viewmodel.DataViewModel
import com.example.drivinglicence.component.fragment.BaseFragment
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.FragmentLessonBinding
import com.example.drivinglicence.utils.ANSWERS
import com.example.drivinglicence.utils.QUESTION
import com.tencent.mmkv.MMKV

class LessonFragment :
    BaseFragment<FragmentLessonBinding, DataViewModel>() {


    private val answerAdapter by lazy {
        AnswerAdapter()
    }

    override fun initView() {
        initData()
        RecyclerUtils.setGridManager(this, binding.rcvAnswers, answerAdapter)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initListener() {
        answerAdapter.setOnClickItemRecyclerView { answer, _ ->
            if (answer.isCorrect) {
                binding.layoutEx.visibility = View.VISIBLE
                binding.layoutExplainAnswer.visibility = View.VISIBLE
                binding.textExplainAnswer.text = answer.answerExplain
                answerAdapter.dataList.map { it.flag = 1 }
                answer.flag = 2
            } else {
                binding.layoutEx.visibility = View.GONE
                binding.layoutExplainAnswer.visibility = View.GONE
                answerAdapter.dataList.map { it.flag = 1 }
                answer.flag = 3
            }

            // ==============================================================
            // == LƯU KẾT QUẢ TRẢ LỜI (ĐÚNG/SAI) VÀO MMKV NGAY TẠI ĐÂY ==
            // ==============================================================
            saveAnswerResult(answer)
            // ==============================================================

            answerAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Hàm này lưu kết quả trả lời của người dùng vào MMKV.
     * - Nếu trả lời đúng, ID câu hỏi sẽ được thêm vào 'CORRECT_ANSWERS_SET'.
     * - Nếu trả lời sai, ID câu hỏi sẽ bị xóa khỏi 'CORRECT_ANSWERS_SET'.
     */
    private fun saveAnswerResult(selectedAnswer: Answer) {
        val mmkv = MMKV.defaultMMKV()
        val questionId = selectedAnswer.questionId.toString()

        // Lấy danh sách (Set) các câu trả lời đúng hiện tại
        val correctAnswersSet = mmkv.decodeStringSet("CORRECT_ANSWERS_SET", mutableSetOf()) ?: mutableSetOf()

        if (selectedAnswer.isCorrect) {
            // Nếu câu trả lời là ĐÚNG, thêm ID câu hỏi vào Set
            correctAnswersSet.add(questionId)
        } else {
            // Nếu câu trả lời là SAI, xóa ID câu hỏi khỏi Set (nếu nó đã tồn tại)
            correctAnswersSet.remove(questionId)
        }

        // Lưu lại Set đã cập nhật vào MMKV
        mmkv.encode("CORRECT_ANSWERS_SET", correctAnswersSet)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetTextI18n")
    override fun initData() {
        val question: Question? = arguments?.getParcelable(QUESTION)
        val listAnswer: MutableList<Answer>? = arguments?.getParcelableArrayList(ANSWERS)
        question?.let {
            if (it.isImportant) {
                binding.textQuestionContent.text = Html.fromHtml(
                    question.content + " " + getString(R.string.text_question_important)
                )
            } else {
                binding.textQuestionContent.text = question.content
            }
            it.image?.let { thumb ->
                binding.imageQuestion.visibility = View.VISIBLE
                binding.imageQuestion.setImageResource(thumb)
            }
        }
        answerAdapter.addData(listAnswer ?: mutableListOf())
    }

    override fun onSingleClick(v: View) {
    }

    override fun onPause() {
        super.onPause()
        answerAdapter.dataList.map { it.flag = 1 }
    }

}
