package com.example.drivinglicence.app.adapter

import com.example.drivinglicence.app.entity.ItemLearning
import com.example.drivinglicence.component.adapter.BaseRecyclerViewAdapter
import com.example.drivinglicence.databinding.ItemLearningBinding
import com.squareup.picasso.Picasso

class LearningAdapter : BaseRecyclerViewAdapter<ItemLearning, ItemLearningBinding>() {
    override fun bindData(binding: ItemLearningBinding, item: ItemLearning, position: Int) {
        binding.textNameItemLearning.text = item.title
        binding.textCountQuestion.text = item.description
        try {
            Picasso.get().load(item.thumb).into(binding.imageItemLearning)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}