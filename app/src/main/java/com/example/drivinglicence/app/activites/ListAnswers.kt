package com.example.drivinglicence.app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListAnswers(
    val listAnswers: MutableList<MutableList<Answer>>
) : Parcelable
