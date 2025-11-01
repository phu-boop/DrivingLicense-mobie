package com.example.drivinglicence.app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    val answerId: Int,
    val answerContent: String,
    val isCorrect: Boolean,
    val questionId: Int,
    val answerExplain: String? = null,
    var flag: Int? = 1,
    var isChoose: Boolean? = false
) : Parcelable
