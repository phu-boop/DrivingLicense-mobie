package com.example.drivinglicence.component.dialog

import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.ListQuestionCountDownBottomAdapter
import com.example.drivinglicence.app.entity.Question
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.ListQuestionBottomDialogBinding
import com.example.drivinglicence.utils.POSITION
import com.example.drivinglicence.utils.QUESTIONS

class ListQuestionCountDownBottomDialog : BaseBottomSheetDialog<ListQuestionBottomDialogBinding>() {

    var onClickItem: ((Int) -> Unit)? = null

    var listQuestion: MutableList<Question> = mutableListOf()
    private val listQuestionAdapter by lazy {
        ListQuestionCountDownBottomAdapter()
    }

    override fun initView() {
        RecyclerUtils.setGridManagerV(
            requireContext(),
            binding.rcvListQuestionBottom,
            5,
            listQuestionAdapter
        )
    }

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun initData() {
        listQuestion = arguments?.getParcelableArrayList(QUESTIONS) ?: mutableListOf()
        arguments?.getInt(POSITION)?.let {
            listQuestionAdapter.currentPos = it
        }
        listQuestionAdapter.addData(listQuestion)
        initListener()
    }

    fun initListener() {
        listQuestionAdapter.setOnClickItemRecyclerView { _, position ->
            onClickItem?.invoke(position)
        }
        binding.iconDown.setOnClickListener {
            onDestroyView()
        }
    }
}