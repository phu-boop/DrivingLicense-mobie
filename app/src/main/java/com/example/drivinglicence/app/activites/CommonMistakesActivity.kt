package com.example.drivinglicence.app.activites

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drivinglicence.app.adapter.MistakesAdapter
import com.example.drivinglicence.component.activity.BaseCoreActivity
import com.example.drivinglicence.databinding.ActivityCommonMistakesBinding
import com.example.drivinglicence.app.viewmodel.CommonMistakesViewModel

// Kế thừa từ BaseCoreActivity và sử dụng ViewBinding
class CommonMistakesActivity : BaseCoreActivity<ActivityCommonMistakesBinding>() {

    // Khởi tạo ViewModel và Adapter bằng 'by lazy' để tối ưu và gọn gàng
    private val viewModel: CommonMistakesViewModel by lazy {
        ViewModelProvider(this)[CommonMistakesViewModel::class.java]
    }
    private val mistakesAdapter by lazy {
        MistakesAdapter(emptyList())
    }

    /**
     * Hàm này dùng để khởi tạo và cài đặt các View.
     */
    override fun initView() {
        // Sử dụng binding để truy cập view, không cần findViewById
        binding.recyclerViewMistakes.apply {
            layoutManager = LinearLayoutManager(this@CommonMistakesActivity)
            adapter = mistakesAdapter
        }
    }

    /**
     * Hàm này dùng để gọi và lắng nghe dữ liệu.
     */
    override fun initData() {
        // Lắng nghe sự thay đổi dữ liệu từ ViewModel
        viewModel.mistakes.observe(this) { mistakesList ->
            // Cập nhật dữ liệu cho adapter khi có dữ liệu mới
            mistakesAdapter.updateData(mistakesList)
        }

        // Yêu cầu ViewModel bắt đầu tải dữ liệu
        viewModel.loadMistakesFromResources()
    }

    /**
     * Hàm này dùng để cài đặt các sự kiện (click, long-click,...).
     */
    override fun initListener() {
        // Giả sử toolbar trong layout có id là 'toolbar' và là một custom view
        // với thuộc tính onLeftClickListener
        binding.toolbar.onLeftClickListener = {
            // Đóng Activity khi người dùng bấm nút back trên toolbar
            finish()
        }
    }

    // Bạn không cần override fun inflateBinding() vì BaseCoreActivity đã xử lý
    // Bạn cũng không cần override onOptionsItemSelected() vì đã xử lý trong initListener
}