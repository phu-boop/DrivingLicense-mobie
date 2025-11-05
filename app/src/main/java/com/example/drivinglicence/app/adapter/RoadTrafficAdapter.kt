package com.example.drivinglicence.app.adapter

import com.example.drivinglicence.app.entity.ItemRoadTraffic
import com.example.drivinglicence.component.adapter.BaseRecyclerViewAdapter
import com.example.drivinglicence.databinding.ItemRoadTrafficBinding
import com.squareup.picasso.Picasso

class RoadTrafficAdapter : BaseRecyclerViewAdapter<ItemRoadTraffic, ItemRoadTrafficBinding>() {
    override fun bindData(binding: ItemRoadTrafficBinding, item: ItemRoadTraffic, position: Int) {
        binding.textTrafficName.text = item.title
        binding.textDescription.text = item.content
        try {
            Picasso.get().load(item.thumb).into(binding.imageThumb)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
}