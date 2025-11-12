package com.example.drivinglicence.app.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.utils.QUESTION
import com.example.drivinglicence.utils.QUESTIONS
import java.util.*

@Suppress("DEPRECATION")
class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    private val fragments: MutableList<Fragment> = mutableListOf()

    fun addFragment(fragment: List<Fragment>) {
        fragments.addAll(fragment)
    }

    fun setFragment(position: Int, fragment: Fragment, question: Question) {
        fragments[position] = fragment.apply {
            val bundle = Bundle()
            bundle.putParcelable(QUESTION, question)
            arguments = bundle
        }
    }

    fun clearFragment() {
        fragments.clear()
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

}