package com.example.drivinglicence.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.drivinglicence.R
import com.example.drivinglicence.app.entity.MistakeQuestion

class CommonMistakesViewModel(application: Application) : AndroidViewModel(application) {

    private val _mistakes = MutableLiveData<List<MistakeQuestion>>()
    val mistakes: LiveData<List<MistakeQuestion>> = _mistakes

    fun loadMistakesFromResources() {
        val context = getApplication<Application>().applicationContext
        val resources = context.resources
        val mistakesList = mutableListOf<MistakeQuestion>()

        // =================== DANH SÁCH CÂU HỎI HAY SAI ===================

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_1),
                answers = listOf(
                    resources.getString(R.string.answer_important_1_1),
                    resources.getString(R.string.answer_important_1_2),
                    resources.getString(R.string.answer_important_1_3),
                    resources.getString(R.string.answer_important_1_4)
                ),
                correctAnswerIndex = 2,
                explanation = resources.getString(R.string.text_explain_answer_important_1)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_2),
                answers = listOf(
                    resources.getString(R.string.answer_important_2_1),
                    resources.getString(R.string.answer_important_2_2),
                    resources.getString(R.string.answer_important_2_3),
                    resources.getString(R.string.answer_important_2_4)
                ),
                correctAnswerIndex = 1,
                explanation = resources.getString(R.string.text_explain_answer_important_2)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_3),
                answers = listOf(
                    resources.getString(R.string.answer_important_3_1),
                    resources.getString(R.string.answer_important_3_2),
                    resources.getString(R.string.answer_important_3_3)
                ),
                correctAnswerIndex = 2,
                explanation = resources.getString(R.string.text_explain_answer_important_3)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_4),
                answers = listOf(
                    resources.getString(R.string.answer_important_4_1),
                    resources.getString(R.string.answer_important_4_2),
                    resources.getString(R.string.answer_important_4_3)
                ),
                correctAnswerIndex = 0,
                explanation = resources.getString(R.string.text_explain_answer_important_4)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_5),
                answers = listOf(
                    resources.getString(R.string.answer_important_5_1),
                    resources.getString(R.string.answer_important_5_2),
                    resources.getString(R.string.answer_important_5_3)
                ),
                correctAnswerIndex = 0,
                explanation = resources.getString(R.string.text_explain_answer_important_5)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_6),
                answers = listOf(
                    resources.getString(R.string.answer_important_6_1),
                    resources.getString(R.string.answer_important_6_2),
                    resources.getString(R.string.answer_important_6_3)
                ),
                correctAnswerIndex = 0,
                explanation = resources.getString(R.string.text_explain_answer_important_6)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_7),
                answers = listOf(
                    resources.getString(R.string.answer_important_7_1),
                    resources.getString(R.string.answer_important_7_2),
                    resources.getString(R.string.answer_important_7_3)
                ),
                correctAnswerIndex = 1,
                explanation = resources.getString(R.string.text_explain_answer_important_7)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_8),
                answers = listOf(
                    resources.getString(R.string.answer_important_8_1),
                    resources.getString(R.string.answer_important_8_2),
                    resources.getString(R.string.answer_important_8_3),
                    resources.getString(R.string.answer_important_8_4)
                ),
                correctAnswerIndex = 0,
                explanation = resources.getString(R.string.text_explain_answer_important_8)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_9),
                answers = listOf(
                    resources.getString(R.string.answer_important_9_1),
                    resources.getString(R.string.answer_important_9_2),
                    resources.getString(R.string.answer_important_9_3),
                    resources.getString(R.string.answer_important_9_4)
                ),
                correctAnswerIndex = 1,
                explanation = resources.getString(R.string.text_explain_answer_important_9)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_10),
                answers = listOf(
                    resources.getString(R.string.answer_important_10_1),
                    resources.getString(R.string.answer_important_10_2),
                    resources.getString(R.string.answer_important_10_3)
                ),
                correctAnswerIndex = 2,
                explanation = resources.getString(R.string.text_explain_answer_important_10)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_11),
                answers = listOf(
                    resources.getString(R.string.answer_important_11_1),
                    resources.getString(R.string.answer_important_11_2),
                    resources.getString(R.string.answer_important_11_3)
                ),
                correctAnswerIndex = 2,
                explanation = resources.getString(R.string.text_explain_answer_important_11)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_12),
                answers = listOf(
                    resources.getString(R.string.answer_important_12_1),
                    resources.getString(R.string.answer_important_12_2),
                    resources.getString(R.string.answer_important_12_3)
                ),
                correctAnswerIndex = 1,
                explanation = resources.getString(R.string.text_explain_answer_important_12)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_13),
                answers = listOf(
                    resources.getString(R.string.answer_important_13_1),
                    resources.getString(R.string.answer_important_13_2),
                    resources.getString(R.string.answer_important_13_3),
                    resources.getString(R.string.answer_important_13_4)
                ),
                correctAnswerIndex = 0,
                explanation = resources.getString(R.string.text_explain_answer_important_13)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_14),
                answers = listOf(
                    resources.getString(R.string.answer_important_14_1),
                    resources.getString(R.string.answer_important_14_2),
                    resources.getString(R.string.answer_important_14_3),
                    resources.getString(R.string.answer_important_14_4)
                ),
                correctAnswerIndex = 2,
                explanation = resources.getString(R.string.text_explain_answer_important_14)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_15),
                answers = listOf(
                    resources.getString(R.string.answer_important_15_1),
                    resources.getString(R.string.answer_important_15_2),
                    resources.getString(R.string.answer_important_15_3)
                ),
                correctAnswerIndex = 1,
                explanation = resources.getString(R.string.text_explain_answer_important_15)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_16),
                answers = listOf(
                    resources.getString(R.string.answer_important_16_1),
                    resources.getString(R.string.answer_important_16_2),
                    resources.getString(R.string.answer_important_16_3)
                ),
                correctAnswerIndex = 2,
                explanation = resources.getString(R.string.text_explain_answer_important_16)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_17),
                answers = listOf(
                    resources.getString(R.string.answer_important_17_1),
                    resources.getString(R.string.answer_important_17_2),
                    resources.getString(R.string.answer_important_17_3)
                ),
                correctAnswerIndex = 0,
                explanation = resources.getString(R.string.text_explain_answer_important_17)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_18),
                answers = listOf(
                    resources.getString(R.string.answer_important_18_1),
                    resources.getString(R.string.answer_important_18_2),
                    resources.getString(R.string.answer_important_18_3)
                ),
                correctAnswerIndex = 1,
                explanation = resources.getString(R.string.text_explain_answer_important_18)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_19),
                answers = listOf(
                    resources.getString(R.string.answer_important_19_1),
                    resources.getString(R.string.answer_important_19_2),
                    resources.getString(R.string.answer_important_19_3)
                ),
                correctAnswerIndex = 0,
                explanation = resources.getString(R.string.text_explain_answer_important_19)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_20),
                answers = listOf(
                    resources.getString(R.string.answer_important_20_1),
                    resources.getString(R.string.answer_important_20_2),
                    resources.getString(R.string.answer_important_20_3)
                ),
                correctAnswerIndex = 1,
                explanation = resources.getString(R.string.text_explain_answer_important_20)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_21),
                answers = listOf(
                    resources.getString(R.string.answer_important_21_1),
                    resources.getString(R.string.answer_important_21_2),
                    resources.getString(R.string.answer_important_21_3),
                    resources.getString(R.string.answer_important_21_4)
                ),
                correctAnswerIndex = 3,
                explanation = resources.getString(R.string.text_explain_answer_important_21)
            )
        )

        mistakesList.add(
            MistakeQuestion(
                question = resources.getString(R.string.question_important_22),
                answers = listOf(
                    resources.getString(R.string.answer_important_22_1),
                    resources.getString(R.string.answer_important_22_2),
                    resources.getString(R.string.answer_important_22_3)
                ),
                correctAnswerIndex = 2,
                explanation = resources.getString(R.string.text_explain_answer_important_22)
            )
        )

        // ================================================================

        _mistakes.value = mistakesList
    }
}
