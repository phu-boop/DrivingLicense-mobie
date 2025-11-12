package com.example.drivinglicence.app.activites

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.RoadTrafficSignAdapter
import com.example.drivinglicence.app.fragment.RoadTrafficBanFragment
import com.example.drivinglicence.app.viewmodel.RoadTrafficViewModel
import com.example.drivinglicence.component.activity.BaseVMActivity
import com.example.drivinglicence.databinding.ActivityRoadTrafficSignsBinding
import com.example.drivinglicence.utils.LIST_ROAD_TRAFFIC

class RoadTrafficSignsActivity :
    BaseVMActivity<ActivityRoadTrafficSignsBinding, RoadTrafficViewModel>() {
    override fun initView() {
        supportActionBar?.hide()
        setupToolBar()
        setUpViewPager()
    }

    private fun setUpViewPager() {
        /**add fragment to view pager*/
        roadTrafficSignAdapter.addTitle(
            mutableListOf(
                "Biển báo cấm",
                "Biển hiệu lệnh",
                "Biển chỉ dẫn",
                "Biển báo nguy hiểm và cảnh báo",
                "Biển phụ"
            )
        )
        val list1 = viewModel.getRoadTrafficBan()
        val list2 = viewModel.getRoadTrafficCommand()
        val list3 = viewModel.getRoadTrafficInstruction()
        val list4 = viewModel.getRoadTrafficWarning()
        val list5 = viewModel.getRoadTrafficSub()
        roadTrafficSignAdapter.addFragment(
            mutableListOf(
                RoadTrafficBanFragment().apply {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(LIST_ROAD_TRAFFIC, list1.shuffled() as ArrayList)
                    arguments = bundle
                },
                RoadTrafficBanFragment().apply {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(LIST_ROAD_TRAFFIC, list2.shuffled() as ArrayList)
                    arguments = bundle
                },
                RoadTrafficBanFragment().apply {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(LIST_ROAD_TRAFFIC, list3.shuffled() as ArrayList)
                    arguments = bundle
                },
                RoadTrafficBanFragment().apply {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(LIST_ROAD_TRAFFIC, list4.shuffled() as ArrayList)
                    arguments = bundle
                },
                RoadTrafficBanFragment().apply {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(LIST_ROAD_TRAFFIC, list5.shuffled() as ArrayList)
                    arguments = bundle
                }
            )
        )

        binding.viewPager.adapter = roadTrafficSignAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private val roadTrafficSignAdapter by lazy {
        RoadTrafficSignAdapter(supportFragmentManager)
    }

    private fun setupToolBar() {
        binding.toolbar.setTitle(getString(R.string.text_road_signs))
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.toolbar.setTitleColor(Color.WHITE)
        binding.toolbar.setIconLeft(R.drawable.icon_back_white)
    }

    override fun initListener() {
        binding.toolbar.onLeftClickListener = { onBackPressed() }
    }

    override fun initData() {
    }
}