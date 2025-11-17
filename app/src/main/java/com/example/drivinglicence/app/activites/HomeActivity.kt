package com.example.drivinglicence.app.activites

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
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
        LocalCache.initialize(this)

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

        val webView = binding.chatbotWebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://10.0.2.2:8080")
        webView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
    }

    private fun setCustomActionBarTitle(title: String) {
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
    }

    private fun initSlide() {
        val listImageSlide =
            arrayListOf(R.drawable.slide1, R.drawable.slide2, R.drawable.slide4, R.drawable.slide5, R.drawable.slide6)
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
    }

    override fun initData() {
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
            initAllList(this@HomeActivity)
            updateLearningProgress()
        }
    }

    override fun initListener() {
        actionAdapter.onCLickItem = { position ->
            when (listAction[position].title) {
                getString(R.string.text_exam) -> {
                    // Quick loading trước khi mở TestLicenseActivity
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
    }

    private fun showQuickLoading() {
        loadingDialog.show(this, "")
        Handler(Looper.getMainLooper()).postDelayed({
            loadingDialog.dismiss()
        }, 300) // Hiển thị nhanh 0.3s
    }

    private fun updateLearningProgress() {
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_license_A1 -> {
                setCustomActionBarTitle(getString(R.string.app_name) + " A1")
                showMessage(this, getString(R.string.text_chose_license_A1))
            }
            R.id.item_license_A2 -> {
                setCustomActionBarTitle(getString(R.string.app_name) + " A2")
                showMessage(this, getString(R.string.text_chose_license_A2))
            }
        }
        return false
    }
}
