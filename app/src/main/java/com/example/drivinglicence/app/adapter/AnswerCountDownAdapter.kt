package com.example.drivinglicence.app.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import com.example.drivinglicence.R
import com.example.drivinglicence.app.entity.Answer
import com.example.drivinglicence.component.adapter.BaseRecyclerViewAdapter
import com.example.drivinglicence.databinding.ItemAnswerBinding

class AnswerCountDownAdapter : BaseRecyclerViewAdapter<Answer, ItemAnswerBinding>() {

    @SuppressLint("SetTextI18n")
    override fun bindData(binding: ItemAnswerBinding, item: Answer, position: Int) {
        binding.textAnswerCount.text = "${position + 1}"
        binding.textAnswerContent.text = item.answerContent
        binding.textAnswerContent.setTextColor(Color.rgb(0, 0, 0))
        binding.textAnswerCount.setTextColor(Color.rgb(0, 0, 0))
        binding.textAnswerCount.setBackgroundResource(R.drawable.bg_round_count)
        binding.imageStatus.visibility = View.GONE
        /** flag
         * 1: black
         * 2: green
         * 3: red
         * */
        when (item.flag) {
            1 -> {
                binding.textAnswerContent.setTextColor(Color.rgb(0, 0, 0))
                binding.textAnswerCount.setTextColor(Color.rgb(0, 0, 0))
                binding.textAnswerCount.setBackgroundResource(R.drawable.bg_round_count)
                binding.imageStatus.visibility = View.GONE
            }
            2 -> {
                binding.textAnswerCount.setTextColor(Color.WHITE)
                binding.textAnswerCount.setBackgroundResource(R.drawable.bg_round_count_orange)
            }
        }
    }
}