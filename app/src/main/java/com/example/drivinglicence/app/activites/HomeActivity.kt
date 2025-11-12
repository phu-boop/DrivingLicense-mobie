package com.example.drivinglicence.app.activites

import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.drivinglicence.R
import com.example.drivinglicence.app.adapter.ActionAdapter
import com.example.drivinglicence.app.entity.ItemAction
import com.example.drivinglicence.component.activity.BaseCoreActivity
import com.example.drivinglicence.component.navigator.openActivity
import com.example.drivinglicence.component.widgets.recyclerview.RecyclerUtils
import com.example.drivinglicence.databinding.ActivityMainBinding
import com.example.drivinglicence.pref.LocalCache
import com.example.drivinglicence.pref.showDevelopMessage
import com.example.drivinglicence.pref.showMessage
import com.example.drivinglicence.utils.initAllList
import com.example.drivinglicence.utils.showDialogDevelopment

class HomeActivity : BaseCoreActivity<ActivityMainBinding>() {
    private var isChatVisible = false

    private val actionAdapter by lazy {
        ActionAdapter()
    }

    private lateinit var listAction: MutableList<ItemAction>

    override fun initView() {
        LocalCache.initialize(this)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
        )
        supportActionBar?.title = getString(R.string.app_name) + " A1"
        initSlide()

        val webView = binding.chatbotWebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://10.0.2.2:8080")

        webView.setBackgroundColor(android.graphics.Color.TRANSPARENT)


    }

    private fun initSlide() {
        val listImageSlide =
            arrayListOf(
                R.drawable.slide1,
                R.drawable.slide2,
                R.drawable.slide4,
                R.drawable.slide5,
                R.drawable.slide6
            )
        for (item in listImageSlide) {
            val imageView = ImageView(this)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setImageResource(item)
            binding.slide.addView(imageView)
        }
        //time to slide
        binding.slide.flipInterval = 1700
        binding.slide.isAutoStart = true
        val animationSlideIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val animationSlideOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
        binding.slide.inAnimation = animationSlideIn
        binding.slide.outAnimation = animationSlideOut
    }

    override fun initData() {
        RecyclerUtils.setGridManager(this, binding.rcvItem, 3, actionAdapter)
        listAction = ArrayList()
        val item1 = ItemAction(getString(R.string.text_exam), R.drawable.exam,R.drawable.border_item_1)
        val item2 = ItemAction(getString(R.string.text_learning_theory), R.drawable.book,R.drawable.border_item_2)
        val item3 = ItemAction(getString(R.string.text_road_signs), R.drawable.stop2,R.drawable.border_item_3)
        val item4 = ItemAction(getString(R.string.text_tips), R.drawable.star,R.drawable.border_item_4)
        val item5 = ItemAction(getString(R.string.text_search_law), R.drawable.law,R.drawable.border_item_5)
        val item6 = ItemAction(getString(R.string.text_sometime_error), R.drawable.computer,R.drawable.border_item_6)
        listAction = arrayListOf(item1, item2, item3, item4, item5,item6)
        actionAdapter.addData(listAction)
        initAllList(this)
    }

    override fun initListener() {
        actionAdapter.onCLickItem = {
            when (listAction[it].title) {
                getString(R.string.text_exam) -> {
                    /**Thi sát hạch*/
                    openActivity(TestLicenseActivity::class.java, false)
                }
                getString(R.string.text_learning_theory) -> {
                    /**Học lý thuyết*/
                    openActivity(LearningTheoryActivity::class.java, false)
                }
                getString(R.string.text_road_signs) -> {
                    /**Biển báo đường bộ*/
                    openActivity(RoadTrafficSignsActivity::class.java, false)
                }
                getString(R.string.text_tips) -> {
                    /**Mẹo đạt kết quả cao*/
                    openActivity(TipsActivity::class.java, false)
                }
                getString(R.string.text_search_law) -> {
                    /**Tra cứu luật*/
                    openActivity(SearchLawActivity::class.java, false)
                }
                getString(R.string.text_sometime_error) -> {
                    /**Các câu hay sai*/
                    openActivity(CommonMistakesActivity::class.java, false)
                }
                else -> {
                    showDialogDevelopment(this)
                }
            }
        }
        binding.btnEdit.setOnClickListener {
            isChatVisible = !isChatVisible
            binding.chatbotWebView.visibility = if (isChatVisible) View.VISIBLE else View.GONE
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_license_A1 -> {
                supportActionBar?.title = getString(R.string.app_name) + " A1"
                showMessage(this, getString(R.string.text_chose_license_A1))
            }

            R.id.item_license_A2 -> {
                supportActionBar?.title = getString(R.string.app_name) + " A2"
                showMessage(this, getString(R.string.text_chose_license_A2))
            }
        }
        return false
    }
}
