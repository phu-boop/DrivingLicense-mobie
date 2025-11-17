package com.example.drivinglicence.app.activites

import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ImageSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
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
import kotlinx.coroutines.launch

class HomeActivity : BaseCoreActivity<ActivityMainBinding>() {
    private var isChatVisible = false
    private val actionAdapter by lazy { ActionAdapter() }
    private lateinit var listAction: MutableList<ItemAction>
    private var customFont: Typeface? = null

    override fun onResume() {
        super.onResume()
        updateLearningProgress()
    }

    override fun initView() {
        try {
            // ⭐ SỬA: Khởi tạo LocalCache TRƯỚC khi dùng
            LocalCache.initialize(this)

            // ⭐ SỬA: Kiểm tra và khởi tạo WebView an toàn
            initWebViewSafely()

            customFont = ResourcesCompat.getFont(this, R.font.ptsansnarrowbold)
            supportActionBar?.setBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(this, R.color.purple_200))
            )
            supportActionBar?.elevation = 0f

            setCustomActionBarTitle(getString(R.string.app_name) + " A1")
            initSlide()

            val rcvItem = binding.rcvItem
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing)
            rcvItem.addItemDecoration(SpacingItemDecoration(spacingInPixels))

            // ⭐ SỬA: Khởi động demo sau khi mọi thứ đã sẵn sàng
            Handler(Looper.getMainLooper()).postDelayed({
                startDemoReminderSafely()
            }, 5000) // Tăng lên 5 giây để đảm bảo app ổn định

        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Error in initView: ${e.message}")
        }
    }

    /**
     * ⭐ SỬA: Khởi tạo WebView an toàn hơn
     */
    private fun initWebViewSafely() {
        try {
            // Kiểm tra xem WebView có sẵn sàng không
            val webView = binding.chatbotWebView

            // Tắt WebView nếu đang debug hoặc có vấn đề
            if (isEmulator() || isDebugBuild()) {
                binding.chatbotWebView.visibility = View.GONE
                binding.btnEdit.visibility = View.GONE
                return
            }

            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true

            // Thêm WebViewClient để xử lý lỗi
            webView.webViewClient = object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    // Ẩn WebView nếu có lỗi
                    runOnUiThread {
                        binding.chatbotWebView.visibility = View.GONE
                        binding.btnEdit.visibility = View.GONE
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    // Kiểm tra nếu trang không load được thì ẩn
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (view?.progress ?: 100 < 80) {
                            binding.chatbotWebView.visibility = View.GONE
                            binding.btnEdit.visibility = View.GONE
                        }
                    }, 3000)
                }
            }

            // Load URL với timeout
            try {
                webView.loadUrl("http://10.0.2.2:8080")
                webView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            } catch (e: Exception) {
                binding.chatbotWebView.visibility = View.GONE
                binding.btnEdit.visibility = View.GONE
            }

        } catch (e: Exception) {
            e.printStackTrace()
            // Ẩn WebView nếu có lỗi khởi tạo
            binding.chatbotWebView.visibility = View.GONE
            binding.btnEdit.visibility = View.GONE
        }
    }

    /**
     * ⭐ Kiểm tra có phải emulator không
     */
    private fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                "google_sdk" == Build.PRODUCT)
    }

    /**
     * ⭐ Kiểm tra có phải debug build không
     */
    private fun isDebugBuild(): Boolean {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            return (packageInfo.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0
        } catch (e: Exception) {
            return false
        }
    }

    /**
     * ⭐ SỬA: Khởi động demo reminder an toàn hơn
     */
    private fun startDemoReminderSafely() {
        try {
            // Kiểm tra xem demo đã chạy chưa
            if (!DailyReminderManager.isDemoReminderEnabled()) {
                println("🚀 Starting demo reminder...")
                DailyReminderManager.enableDemoReminder(this, 15)
            } else {
                println("🔔 Demo reminder is already running")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Failed to start demo reminder: ${e.message}")
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
            e.printStackTrace()
        }
    }

    private fun initSlide() {
        try {
            val listImageSlide = arrayListOf(
                R.drawable.slide1, R.drawable.slide2, R.drawable.slide4,
                R.drawable.slide5, R.drawable.slide6
            )
            for (item in listImageSlide) {
                val imageView = ImageView(this)
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.setImageResource(item)
                binding.slide.addView(imageView)
            }
            binding.slide.flipInterval = 1700
            binding.slide.isAutoStart = true
            val animationSlideIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
            val animationSlideOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
            binding.slide.inAnimation = animationSlideIn
            binding.slide.outAnimation = animationSlideOut

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initData() {
        try {
            RecyclerUtils.setGridManager(this, binding.rcvItem, 2, actionAdapter)
            listAction = ArrayList()

            val item1 = ItemAction(getString(R.string.text_exam), R.drawable.exam, R.drawable.border_item_1)
            val item2 = ItemAction(getString(R.string.text_learning_theory), R.drawable.book, R.drawable.border_item_2)
            val item3 = ItemAction(getString(R.string.text_road_signs), R.drawable.stop2, R.drawable.border_item_3)
            val item4 = ItemAction(getString(R.string.text_tips), R.drawable.star, R.drawable.border_item_4)
            val item5 = ItemAction(getString(R.string.text_search_law), R.drawable.law, R.drawable.border_item_5)
            val item6 = ItemAction(getString(R.string.text_sometime_error), R.drawable.computer, R.drawable.border_item_6)

            listAction = arrayListOf(item1, item2, item3, item4, item5, item6)
            actionAdapter.addData(listAction)

            lifecycleScope.launch {
                try {
                    initAllList(this@HomeActivity)
                    updateLearningProgress()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initListener() {
        try {
            actionAdapter.onCLickItem = { position ->
                when (listAction[position].title) {
                    getString(R.string.text_exam) -> {
                        showQuickLoading()
                        openActivity(TestLicenseActivity::class.java, false)
                    }
                    getString(R.string.text_learning_theory) -> openActivity(LearningTheoryActivity::class.java, false)
                    getString(R.string.text_road_signs) -> openActivity(RoadTrafficSignsActivity::class.java, false)
                    getString(R.string.text_tips) -> openActivity(TipsActivity::class.java, false)
                    getString(R.string.text_search_law) -> openActivity(SearchLawActivity::class.java, false)
                    getString(R.string.text_sometime_error) -> openActivity(CommonMistakesActivity::class.java, false)
                    else -> showDialogDevelopment(this)
                }
            }

            binding.btnEdit.setOnClickListener {
                isChatVisible = !isChatVisible
                binding.chatbotWebView.visibility = if (isChatVisible) View.VISIBLE else View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showQuickLoading() {
        try {
            loadingDialog.show(this, "")
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    loadingDialog.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, 300)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateLearningProgress() {
        try {
            val totalQuestions = getTotalTheoryQuestionCount()
            binding.layoutProgress.visibility = View.VISIBLE

            val mmkv = MMKV.defaultMMKV()
            val viewedQuestionsSet = mmkv.decodeStringSet("VIEWED_QUESTIONS_SET", emptySet()) ?: emptySet()
            val correctAnswersMap = mmkv.decodeStringSet("CORRECT_ANSWERS_SET", emptySet()) ?: emptySet()

            val questionsDone = viewedQuestionsSet.size
            val correctAnswersCount = correctAnswersMap.size

            val progressPercentage = if (totalQuestions > 0) (questionsDone * 100) / totalQuestions else 0
            val correctRate = if (questionsDone > 0) (correctAnswersCount * 100) / questionsDone else 0

            binding.progressCircular.progress = progressPercentage
            binding.tvProgressPercentage.text = "$progressPercentage%"
            binding.tvQuestionsDoneValue.text = "$questionsDone/$totalQuestions câu"
            binding.progressBarQuestions.max = 100
            binding.progressBarQuestions.progress = progressPercentage
            binding.tvCorrectRateValue.text = "$correctRate%"
            binding.progressBarCorrectRate.progress = correctRate

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        try {
            menuInflater.inflate(R.menu.menu_setting, menu)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
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
                    toggleDailyReminder()
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /** ⭐ Hàm bật/tắt nhắc nhở */
    private fun toggleDailyReminder() {
        try {
            val isDemoEnabled = DailyReminderManager.isDemoReminderEnabled()

            if (isDemoEnabled) {
                DailyReminderManager.disableDemoReminder(this)
                showMessage(this, "Đã tắt demo nhắc nhở 15 giây")
            } else {
                DailyReminderManager.enableDemoReminder(this, 15)
                showMessage(this, "Đã bật demo nhắc nhở 15 giây")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showMessage(this, "Lỗi khi thao tác với nhắc nhở")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Dọn dẹp khi activity bị destroy
        try {
            // Có thể tắt demo ở đây nếu muốn
            // DailyReminderManager.disableDemoReminder(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}