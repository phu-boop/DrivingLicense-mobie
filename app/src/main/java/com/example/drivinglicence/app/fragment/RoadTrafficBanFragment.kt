package com.example.drivinglicence.app.fragment

import androidx.recyclerview.widget.DividerItemDecoration
import com.example.drivinglicence.app.adapter.RoadTrafficAdapter
import com.example.drivinglicence.app.entity.ItemRoadTraffic
import com.example.drivinglicence.app.viewmodel.MapDataViewModel
import com.example.drivinglicence.component.fragment.BaseFragment
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.FragmentRoadTrafficBanBinding
import com.example.drivinglicence.utils.LIST_ROAD_TRAFFIC


class RoadTrafficBanFragment : BaseFragment<FragmentRoadTrafficBanBinding, MapDataViewModel>() {

    private val roadTrafficAdapter by lazy {
        RoadTrafficAdapter()
    }

    override fun initView() {
        RecyclerUtils.setGridManager(this, binding.rcvRoadTraffic, roadTrafficAdapter)
        binding.rcvRoadTraffic.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun initListener() {
    }

    override fun initData() {
        val dataList =
            arguments?.getParcelableArrayList(LIST_ROAD_TRAFFIC) ?: mutableListOf<ItemRoadTraffic>()
        roadTrafficAdapter.addData(dataList)
    }
}