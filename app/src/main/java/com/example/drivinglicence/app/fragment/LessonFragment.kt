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

            saveAnswerResult(answer)

            answerAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Hàm này lưu kết quả trả lời của người dùng vào MMKV.
     * - Nếu trả lời đúng, ID câu hỏi sẽ được thêm vào 'CORRECT_ANSWERS_SET'.
     * - Nếu trả lời sai, ID câu hỏi sẽ bị xóa khỏi 'CORRECT_ANSWERS_SET'.
     */
    /**
     * Hàm này lưu kết quả trả lời vào MMKV để HomeActivity đọc được.
     */
    private fun saveAnswerResult(selectedAnswer: Answer) {
        val mmkv = MMKV.defaultMMKV()
        val questionId = selectedAnswer.questionId.toString()

        // 1. LƯU DANH SÁCH CÂU ĐÃ HỌC (VIEWED_QUESTIONS_SET)
        val viewedSet = mmkv.decodeStringSet("VIEWED_QUESTIONS_SET", mutableSetOf()) ?: mutableSetOf()
        viewedSet.add(questionId)
        mmkv.encode("VIEWED_QUESTIONS_SET", viewedSet)

        // 2. LƯU DANH SÁCH CÂU ĐÚNG (CORRECT_ANSWERS_SET)
        val correctAnswersSet = mmkv.decodeStringSet("CORRECT_ANSWERS_SET", mutableSetOf()) ?: mutableSetOf()

        if (selectedAnswer.isCorrect) {
            correctAnswersSet.add(questionId)
        } else {
            correctAnswersSet.remove(questionId)
        }
        mmkv.encode("CORRECT_ANSWERS_SET", correctAnswersSet)

        // 3. MỚI: Lưu cụ thể người dùng đã chọn đáp án nào (để hiển thị lại màu đỏ/xanh khi mở lại)

        viewModel.saveUserChoice(selectedAnswer.questionId, selectedAnswer.answerId)

    }



    @Suppress("DEPRECATION")
    @SuppressLint("SetTextI18n")
    override fun initData() {
        val question: Question? = arguments?.getParcelable(QUESTION)
        val listAnswer: MutableList<Answer>? = arguments?.getParcelableArrayList(ANSWERS)

        question?.let { q ->
            if (q.isImportant) {
                binding.textQuestionContent.text = Html.fromHtml(
                    q.content + " " + getString(R.string.text_question_important)
                )
            } else {
                binding.textQuestionContent.text = q.content
            }
            q.image?.let { thumb ->
                binding.imageQuestion.visibility = View.VISIBLE
                binding.imageQuestion.setImageResource(thumb)
            }

            restoreOldState(q.questionId, listAnswer)
        }

        answerAdapter.addData(listAnswer ?: mutableListOf())
    }

    /**
     * Hàm này kiểm tra xem câu hỏi đã từng làm chưa.
     * Nếu đã làm:
     * - Tô màu đáp án đúng (Màu xanh).
     * - Nếu trước đó làm sai, có thể tô màu đáp án sai (tùy logic bạn muốn, ở đây mình ưu tiên hiện đáp án đúng để học).
     * - Hiện giải thích.
     */
    private fun restoreOldState(questionId: Int, listAnswer: MutableList<Answer>?) {
        if (listAnswer == null) return

        // Lấy đáp án người dùng đã chọn trước đó từ bộ nhớ
        val userSelectedId = viewModel.getUserChoice(questionId)

        if (userSelectedId != -1) {
            // Tìm đáp án đúng thực sự của câu hỏi
            val correctAnswer = listAnswer.find { it.isCorrect }

            val selectedAnswer = listAnswer.find { it.answerId == userSelectedId }


            if (selectedAnswer != null && correctAnswer != null) {

                if (selectedAnswer.isCorrect) {
                    selectedAnswer.flag = 2
                } else {

                    selectedAnswer.flag = 3
                    correctAnswer.flag = 2
                }

                binding.layoutEx.visibility = View.VISIBLE
                binding.layoutExplainAnswer.visibility = View.VISIBLE
                binding.textExplainAnswer.text = correctAnswer.answerExplain
            }
        }
    }



    override fun onSingleClick(v: View) {
    }

    override fun onPause() {
        super.onPause()
        answerAdapter.dataList.map { it.flag = 1 }
    }

}
