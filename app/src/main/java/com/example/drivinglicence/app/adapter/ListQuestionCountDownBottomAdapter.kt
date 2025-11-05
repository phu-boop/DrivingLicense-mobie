package com.example.drivinglicence.app.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.drivinglicence.R
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.component.adapter.BaseRecyclerViewAdapter
import com.example.drivinglicence.databinding.ItemListQuestionBottomBinding

class ListQuestionCountDownBottomAdapter :
    BaseRecyclerViewAdapter<Question, ItemListQuestionBottomBinding>() {
    var currentPos: Int = 0

    @SuppressLint("SetTextI18n")
    override fun bindData(binding: ItemListQuestionBottomBinding, item: Question, position: Int) {
        binding.textCount.text = "${position + 1}"
        if (item.isChooseAnswer == true) {
            binding.imageChoose.visibility = View.VISIBLE
        }
        if (position != currentPos) {
            binding.textCount.setBackgroundResource(R.drawable.circle_button_grey)
        } else {
            binding.textCount.setBackgroundResource(R.drawable.circle_button_grey_border)
        }
    }

}