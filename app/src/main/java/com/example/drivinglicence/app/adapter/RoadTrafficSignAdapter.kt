package com.example.drivinglicence.app.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

@Suppress("DEPRECATION")
class RoadTrafficSignAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val mListFragment = mutableListOf<Fragment>()
    private val mListTitle = mutableListOf<String>()

    fun addFragment(fragmentList: MutableList<Fragment>) {
        mListFragment.addAll(fragmentList)
    }

    fun addTitle(titleList: MutableList<String>) {
        mListTitle.addAll(titleList)
    }


    override fun getCount(): Int {
        return mListFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return mListFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mListTitle[position]
    }
}