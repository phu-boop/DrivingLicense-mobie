package com.example.drivinglicence.app.activites

import android.app.TimePickerDialog
import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.Manifest
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.ActionAdapter
import com.example.drivinglicence.app.entity.ItemAction
import com.example.drivinglicence.component.activity.BaseCoreActivity
import com.example.drivinglicence.component.navigator.openActivity
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.ActivityMainBinding
import com.example.drivinglicence.pref.LocalCache
import com.example.drivinglicence.pref.showMessage
import com.example.drivinglicence.utils.*
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Calendar

class HomeActivity : BaseCoreActivity<ActivityMainBinding>() {
    private var isChatVisible = false
    private val actionAdapter by lazy { ActionAdapter() }
    private lateinit var listAction: MutableList<ItemAction>
    private var customFont: Typeface? = null // Sửa: Cho phép null
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onResume() {
        super.onResume()
        mainHandler.postDelayed({
            updateLearningProgress()
            updateReminderStatus()
        }, 500)
    }

    override fun initView() {
        try {
            LocalCache.initialize(this)
            createNotificationChannel()

            lifecycleScope.launch(Dispatchers.IO) {
                loadFontAndSetupActionBar()
            }

            mainHandler.postDelayed({
                initWebViewSafely()
            }, 1000)

            initSlideWithDelay()
            setupRecyclerView()
            updateReminderStatus()

        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi trong initView", e)
        }
    }

    private fun createNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "daily_study_reminder",
                    "Nhắc nhở học tập",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Nhắc nhở ôn tập lái xe hàng ngày"
                    enableLights(true)
                    lightColor = Color.RED
                    enableVibration(true)
                    vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)
                    lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                }

                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
                Log.d("HomeActivity", "✅ Kênh thông báo đã được tạo")
            }
        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi tạo kênh thông báo", e)
        }
    }

    private suspend fun loadFontAndSetupActionBar() {
        try {
            customFont = ResourcesCompat.getFont(this@HomeActivity, R.font.ptsansnarrowbold)

            withContext(Dispatchers.Main) {
                supportActionBar?.setBackgroundDrawable(
                    ColorDrawable(ContextCompat.getColor(this@HomeActivity, R.color.purple_200))
                )
                supportActionBar?.elevation = 0f
                setCustomActionBarTitle(getString(R.string.app_name) + " A1")
            }
        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi tải font", e)
        }
    }

    private fun initWebViewSafely() {
        try {
            if (isRunningOnEmulator()) {
                Log.d("HomeActivity", "Máy ảo → tắt chatbot")
                binding.chatbotWebView.visibility = View.GONE
                binding.btnEdit.visibility = View.GONE
                return
            }

            val webView = binding.chatbotWebView
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true
            //webView.settings.cacheMode = WebSettings.LOAD_DEFAULT

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    Log.d("WebView", "✅ Đã tải HTML thành công: $url")
                    binding.chatbotWebView.visibility = View.VISIBLE
                    binding.btnEdit.visibility = View.VISIBLE
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.e("WebView", "⚠️ Lỗi: ${error}")
                    // Không ẩn, chỉ log
                }
            }

            // LOAD TỪ ASSETS - KHÔNG BAO GIỜ "NOT AVAILABLE"
            webView.loadUrl("file:///android_asset/chat.html")
            webView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            Log.d("WebView", "🔄 Đang tải chat từ assets...")

        } catch (e: Exception) {
            Log.e("HomeActivity", "Lỗi khởi tạo WebView", e)
            binding.chatbotWebView.visibility = View.VISIBLE
            binding.btnEdit.visibility = View.VISIBLE
        }
    }

    // Thêm hàm retry nếu connect chậm (tùy chọn, gọi từ onReceivedError)
    private fun retryLoadWebView(webView: WebView) {
        mainHandler.postDelayed({
            Log.d("WebView", "🔄 Retry load...")
            webView.loadUrl("https://gemini-vn-chat.pages.dev/v2")
        }, 2000)  // Retry sau 2 giây
    }

    private fun isRunningOnEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                "google_sdk" == Build.PRODUCT)
    }

    private fun initSlideWithDelay() {
        mainHandler.postDelayed({
            try {
                val listImageSlide = arrayListOf(
                    R.drawable.slide1, R.drawable.slide2, R.drawable.slide4,
                    R.drawable.slide5, R.drawable.slide6
                )

                binding.slide.removeAllViews()

                for (item in listImageSlide) {
                    val imageView = ImageView(this)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                    imageView.setImageResource(item)
                    binding.slide.addView(imageView)
                }

                binding.slide.flipInterval = 2000
                binding.slide.isAutoStart = true

                val animationSlideIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
                val animationSlideOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
                binding.slide.inAnimation = animationSlideIn
                binding.slide.outAnimation = animationSlideOut

            } catch (e: Exception) {
                Log.e("HomeActivity", "❌ Lỗi khởi tạo slide", e)
            }
        }, 1500)
    }

    private fun setupRecyclerView() {
        try {
            val rcvItem = binding.rcvItem
            val spacingInPixels =
                resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing)
            rcvItem.addItemDecoration(SpacingItemDecoration(spacingInPixels))
            RecyclerUtils.setGridManager(this, rcvItem, 2, actionAdapter)
        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi thiết lập RecyclerView", e)
        }
    }

    private fun setCustomActionBarTitle(title: String) {
        try {
            val drawable = ContextCompat.getDrawable(this, R.drawable.driving) ?: return
            drawable.setBounds(0, 0, 90, 90)

            val fullTitle = " $title"
            val spannableString = SpannableString("   $title")
            val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM)
            spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

            // Sửa: Sử dụng safe call với let
            customFont?.let { font ->
                spannableString.setSpan(
                    CustomTypefaceSpan(font),
                    1,
                    fullTitle.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }

            val textSizeInSp = 24
            val textSizeInPx = (textSizeInSp * resources.displayMetrics.scaledDensity).toInt()
            spannableString.setSpan(
                AbsoluteSizeSpan(textSizeInPx),
                1,
                fullTitle.length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            supportActionBar?.title = spannableString

        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi thiết lập tiêu đề action bar", e)
        }
    }

    override fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                listAction = ArrayList()

                val item1 = ItemAction(
                    getString(R.string.text_exam),
                    R.drawable.exam,
                    R.drawable.border_item_1
                )
                val item2 = ItemAction(
                    getString(R.string.text_learning_theory),
                    R.drawable.book,
                    R.drawable.border_item_2
                )
                val item3 = ItemAction(
                    getString(R.string.text_road_signs),
                    R.drawable.stop2,
                    R.drawable.border_item_3
                )
                val item4 = ItemAction(
                    getString(R.string.text_tips),
                    R.drawable.star,
                    R.drawable.border_item_4
                )
                val item5 = ItemAction(
                    getString(R.string.text_search_law),
                    R.drawable.law,
                    R.drawable.border_item_5
                )
                val item6 = ItemAction(
                    getString(R.string.text_sometime_error),
                    R.drawable.computer,
                    R.drawable.border_item_6
                )

                listAction = arrayListOf(item1, item2, item3, item4, item5, item6)

                withContext(Dispatchers.Main) {
                    actionAdapter.addData(listAction)
                }

                initAllList(this@HomeActivity)

                withContext(Dispatchers.Main) {
                    updateLearningProgress()
                }

            } catch (e: Exception) {
                Log.e("HomeActivity", "❌ Lỗi trong initData", e)
            }
        }
    }

    override fun initListener() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        101
                    )
                }
            }
            actionAdapter.onCLickItem = { position ->
                when (listAction[position].title) {
                    getString(R.string.text_exam) -> {
                        showQuickLoading()
                        openActivity(TestLicenseActivity::class.java, false)
                    }

                    getString(R.string.text_learning_theory) -> openActivity(
                        LearningTheoryActivity::class.java,
                        false
                    )

                    getString(R.string.text_road_signs) -> openActivity(
                        RoadTrafficSignsActivity::class.java,
                        false
                    )

                    getString(R.string.text_tips) -> openActivity(TipsActivity::class.java, false)
                    getString(R.string.text_search_law) -> openActivity(
                        SearchLawActivity::class.java,
                        false
                    )

                    getString(R.string.text_sometime_error) -> openActivity(
                        CommonMistakesActivity::class.java,
                        false
                    )

                    else -> showDialogDevelopment(this)
                }
            }

            // Click ngắn để mở Chatbot
            binding.btnEdit.setOnClickListener {
                isChatVisible = !isChatVisible
                binding.chatbotWebView.visibility = if (isChatVisible) View.VISIBLE else View.GONE
            }

            // Click giữ lâu để mở Quản lý nhắc nhở (Giải pháp tạm thời nếu không muốn thêm nút)
            binding.btnEdit.setOnLongClickListener {
                showReminderManagementDialog()
                true
            }

            // HOẶC: Nếu trong giao diện bạn có menu option, hãy dùng menu (bạn đã làm trong onOptionsItemSelected rồi)

        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi trong initListener", e)
        }
    }

    /**
     * ⭐ CẬP NHẬT TRẠNG THÁI NHẮC NHỞ
     */
    private fun updateReminderStatus() {
        try {
            val isEnabled = DailyReminderManager.isDailyReminderEnabled()
            val (hour, minute) = DailyReminderManager.getReminderTime()

            // Sửa: Đơn giản hóa - chỉ log trạng thái
            Log.d(
                "ReminderStatus",
                "🔄 Trạng thái nhắc nhở: ${if (isEnabled) "BẬT" else "TẮT"} - ${
                    DailyReminderManager.formatTime(
                        hour,
                        minute
                    )
                }"
            )

        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi cập nhật trạng thái", e)
        }
    }

    /**
     * ⭐ HIỂN THỊ DIALOG QUẢN LÝ NHẮC NHỞ
     */
    private fun showReminderManagementDialog() {
        val isEnabled = DailyReminderManager.isDailyReminderEnabled()
        // Truyền 'this' vào hàm getReminderTime để an toàn
        val (hour, minute) = DailyReminderManager.getReminderTime(this)

        val options = arrayOf(
            "🕐 Đặt giờ nhắc nhở (Hiện tại: ${DailyReminderManager.formatTime(hour, minute)})",
            "📊 Xem trạng thái nhắc nhở",
            if (isEnabled) "❌ Tắt nhắc nhở" else "✅ Bật nhắc nhở"
        )

        AlertDialog.Builder(this)
            .setTitle("Quản lý nhắc nhở học tập")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showTimePickerDialog()
                    1 -> showReminderStatus()
                    2 -> toggleDailyReminder()
                }
            }
            .setNegativeButton("Đóng", null)
            .show()
    }

    /**
     * ⭐ HIỂN THỊ BỘ CHỌN GIỜ
     */
    private fun showTimePickerDialog() {
        val (currentHour, currentMinute) = DailyReminderManager.getReminderTime(this)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                // Lưu thời gian mới
                if (DailyReminderManager.canScheduleExactAlarms(this)) {
                    DailyReminderManager.enableDailyReminder(this, hourOfDay, minute)
                    showMessage(
                        this,
                        "✅ Đã đặt nhắc nhở lúc ${
                            DailyReminderManager.formatTime(hourOfDay, minute)
                        } hàng ngày"
                    )
                    updateReminderStatus()

                    // Hiển thị thông báo xác nhận
                    showReminderSetConfirmation(hourOfDay, minute)
                } else {
                    showMessage(this, "❌ Cần cấp quyền exact alarm để đặt nhắc nhở chính xác")
                }
            },
            currentHour,
            currentMinute,
            true // 24-hour format
        )

        timePickerDialog.setTitle("Chọn giờ nhắc nhở hàng ngày")
        timePickerDialog.show()
    }

    /**
     * ⭐ HIỂN THỊ XÁC NHẬN ĐÃ ĐẶT NHẮC NHỞ
     */
    private fun showReminderSetConfirmation(hour: Int, minute: Int) {
        AlertDialog.Builder(this)
            .setTitle("✅ Đã đặt nhắc nhở")
            .setMessage(
                "Bạn sẽ nhận được thông báo ôn tập mỗi ngày lúc ${
                    DailyReminderManager.formatTime(hour, minute)
                }\n\n" +
                        "Thông báo sẽ hiển thị ngay cả khi app đang chạy nền hoặc đã đóng."
            )
            .setPositiveButton("OK", null)
            .show()
    }

    /**
     * ⭐ HIỂN THỊ TRẠNG THÁI NHẮC NHỞ
     */
    private fun showReminderStatus() {
        val isEnabled = DailyReminderManager.isDailyReminderEnabled()
        val (hour, minute) = DailyReminderManager.getReminderTime(this)

        val statusMessage = if (isEnabled) {
            "📊 TRẠNG THÁI: ĐANG BẬT\n\n" +
                    "🕐 THỜI GIAN: ${DailyReminderManager.formatTime(hour, minute)} hàng ngày\n" +
                    "🔊 ÂM THANH: Có\n" +
                    "📳 RUNG: Có\n\n" +
                    "Bạn sẽ nhận được thông báo ôn tập mỗi ngày!"
        } else {
            "📊 TRẠNG THÁI: ĐANG TẮT\n\n" +
                    "Hãy bật nhắc nhở để không quên ôn tập mỗi ngày!"
        }

        AlertDialog.Builder(this)
            .setTitle("Trạng thái nhắc nhở")
            .setMessage(statusMessage)
            .setPositiveButton("OK", null)
            .show()
    }

    /**
     * ⭐ BẬT/TẮT NHẮC NHỞ
     */
    private fun toggleDailyReminder() {
        try {
            val isEnabled = DailyReminderManager.isDailyReminderEnabled()

            if (isEnabled) {
                // TẮT nhắc nhở
                DailyReminderManager.disableDailyReminder(this)
                showMessage(this, "✅ Đã tắt nhắc nhở hàng ngày")
            } else {
                // BẬT nhắc nhở với thời gian hiện tại
                val (hour, minute) = DailyReminderManager.getReminderTime(this)
                if (DailyReminderManager.canScheduleExactAlarms(this)) {
                    DailyReminderManager.enableDailyReminder(this, hour, minute)
                    showMessage(
                        this,
                        "✅ Đã bật nhắc nhở lúc ${
                            DailyReminderManager.formatTime(hour, minute)
                        } hàng ngày"
                    )
                } else {
                    showMessage(this, "❌ Cần cấp quyền exact alarm cho nhắc nhở chính xác")
                }
            }
            updateReminderStatus()
        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi khi bật/tắt nhắc nhở", e)
            showMessage(this, "❌ Lỗi: ${e.message}")
        }
    }

    private fun showQuickLoading() {
        try {
            loadingDialog.show(this, "")
            mainHandler.postDelayed({
                try {
                    loadingDialog.dismiss()
                } catch (e: Exception) {
                    Log.e("HomeActivity", "❌ Lỗi đóng loading", e)
                }
            }, 300)
        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi hiển thị loading", e)
        }
    }




    private fun updateLearningProgress() {
        try {
            val totalQuestions = getTotalTheoryQuestionCount()
            binding.layoutProgress.visibility = View.VISIBLE

            val mmkv = MMKV.defaultMMKV()
            val viewedQuestionsSet =
                mmkv.decodeStringSet("VIEWED_QUESTIONS_SET", emptySet()) ?: emptySet()
            val correctAnswersMap =
                mmkv.decodeStringSet("CORRECT_ANSWERS_SET", emptySet()) ?: emptySet()

            val questionsDone = viewedQuestionsSet.size
            val correctAnswersCount = correctAnswersMap.size

            val progressPercentage =
                if (totalQuestions > 0) (questionsDone * 100) / totalQuestions else 0
            val correctRate =
                if (questionsDone > 0) (correctAnswersCount * 100) / questionsDone else 0

            if (binding.progressCircular.progress != progressPercentage) {
                binding.progressCircular.progress = progressPercentage
            }

            binding.tvProgressPercentage.text = "$progressPercentage%"
            binding.tvQuestionsDoneValue.text = "$questionsDone/$totalQuestions câu"

            if (binding.progressBarQuestions.progress != progressPercentage) {
                binding.progressBarQuestions.max = 100
                binding.progressBarQuestions.progress = progressPercentage
            }

            binding.tvCorrectRateValue.text = "$correctRate%"

            if (binding.progressBarCorrectRate.progress != correctRate) {
                binding.progressBarCorrectRate.progress = correctRate
            }

        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi cập nhật tiến độ", e)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        try {
            menuInflater.inflate(R.menu.menu_setting, menu)
            return true
        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi tạo options menu", e)
            return false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            when (item.itemId) {
                R.id.item_license_A1 -> {
                    setCustomActionBarTitle(getString(R.string.app_name) + " A1")
                    showMessage(this, getString(R.string.text_chose_license_A1))
                }

                R.id.item_license_A2 -> {
                    setCustomActionBarTitle(getString(R.string.app_name) + " A2")
                    showMessage(this, getString(R.string.text_chose_license_A2))
                }

                R.id.item_daily_reminder -> {
                    showReminderManagementDialog()
                }
            }
            return true
        } catch (e: Exception) {
            Log.e("HomeActivity", "❌ Lỗi trong options item selected", e)
            return false
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeActivity", "🟡 App chuyển sang nền - Nhắc nhở sẽ tiếp tục hoạt động")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "🟡 App bị hủy - Nhắc nhở vẫn sẽ hoạt động")
        mainHandler.removeCallbacksAndMessages(null)
    }
}