package com.example.drivinglicence.app.entity

data class MistakeQuestion(
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)