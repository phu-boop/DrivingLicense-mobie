package com.example.drivinglicence.app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val questionId: Int,
    val examId: Int?,
    val content: String,
    val image: Int?,
    val isImportant: Boolean, //câu điểm liệt (sai => trượt)
    var isChooseAnswer: Boolean? = false
) : Parcelable