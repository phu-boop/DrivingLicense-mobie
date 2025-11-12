package com.example.drivinglicence.app.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.drivinglicence.R
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.component.adapter.BaseRecyclerViewAdapter
import com.example.drivinglicence.databinding.ItemListQuestionBottomBinding

class ListQuestionBottomAdapter :
    BaseRecyclerViewAdapter<Question, ItemListQuestionBottomBinding>() {
    var currentPos: Int = 0
    @SuppressLint("SetTextI18n")
    override fun bindData(binding: ItemListQuestionBottomBinding, item: Question, position: Int) {
//        binding.textCount.text = "${item.questionId}"
        binding.textCount.text = "${position+1}"
        if (item.isChooseAnswer == true) {
            binding.imageChoose.visibility = View.VISIBLE
        }
        if (position != currentPos) {
            if (item.isImportant) {
                binding.textCount.setBackgroundResource(R.drawable.circle_button_orange)
            } else {
                binding.textCount.setBackgroundResource(R.drawable.circle_button_grey)
            }
        } else {
            if (item.isImportant) {
                binding.textCount.setBackgroundResource(R.drawable.circle_button_orange_border)
            } else {
                binding.textCount.setBackgroundResource(R.drawable.circle_button_grey_border)
            }
        }
    }

}