package com.example.drivinglicence.app.entity

data class Exam(
    val examId: Int,
    val name: String,
    val type: Int /**1 - A1, 2 - A2*/
)