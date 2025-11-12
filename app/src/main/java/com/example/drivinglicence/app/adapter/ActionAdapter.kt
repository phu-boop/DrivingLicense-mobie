package com.example.drivinglicence.app.adapter

import androidx.core.content.ContextCompat
import com.example.drivinglicence.app.entity.ItemAction
import com.example.drivinglicence.component.adapter.BaseRecyclerViewAdapter
import com.example.drivinglicence.databinding.ItemActionHomeBinding
import com.squareup.picasso.Picasso
import java.lang.Exception

class ActionAdapter : BaseRecyclerViewAdapter<ItemAction, ItemActionHomeBinding>() {
    var onCLickItem: ((position: Int) -> Unit)? = null
    override fun bindData(binding: ItemActionHomeBinding, item: ItemAction, position: Int) {
        binding.tvTitle.text = item.title
        try {
            Picasso.get().load(item.thumb).into(binding.imageThumb)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        binding.imageThumb.setBackgroundResource(item.color)
        binding.layoutItemAction.setOnClickListener { onCLickItem?.invoke(position) }
    }
}