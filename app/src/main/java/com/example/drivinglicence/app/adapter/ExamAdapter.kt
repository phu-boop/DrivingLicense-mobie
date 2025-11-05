package com.example.drivinglicence.app.adapter

import android.annotation.SuppressLint
import com.example.drivinglicence.R
import com.example.drivinglicence.component.adapter.BaseRecyclerViewAdapter
import com.example.drivinglicence.databinding.ItemExamBinding

class ExamAdapter : BaseRecyclerViewAdapter<Int, ItemExamBinding>() {
    @SuppressLint("SetTextI18n")
    override fun bindData(binding: ItemExamBinding, item: Int, position: Int) {
        binding.tvCount.text = context.getString(R.string.text_exam_number) + " $item"
    }
}