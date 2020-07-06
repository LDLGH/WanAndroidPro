package com.ldl.wanandroidpro.ui.main.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.base.activity.BaseActivity
import com.ldl.wanandroidpro.databinding.ActivityWebBinding
import com.ldl.wanandroidpro.viewmodel.main.WebViewViewModel
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.common_toolbar.*

/**
 * 作者：LDL 创建时间：2020/6/2
 * 类说明：
 */
class WebViewActivity : BaseActivity<ActivityWebBinding>() {

    companion object {
        private const val TITLE = "title"
        private const val URL = "url"

        fun start(title: String, url: String) {
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putString(URL, url)
            ActivityUtils.startActivity(bundle, WebViewActivity::class.java)
        }
    }

    private val viewModel by viewModels<WebViewViewModel>()

    private var mAgentWeb: AgentWeb? = null

    private var toolbarTitle = ""
    private var url = ""

    override fun getLayoutId(): Int  = R.layout.activity_web

    override fun onViewCreated() {
        super.onViewCreated()
        binding.viewModel = viewModel
        getBundleData()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = toolbarTitle
        initWebView()
    }

    override fun initToolbar() {

    }

    override fun initData() {

    }

    private fun initWebView() {
        val webView = NestedScrollAgentWebView(this)
        val lp = CoordinatorLayout.LayoutParams(-1, -1)
        lp.behavior = AppBarLayout.ScrollingViewBehavior()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(coordinator_main, 1, lp)
            .useDefaultIndicator(ColorUtils.getColor(R.color.colorAccent))
            .setWebView(webView)
            .createAgentWeb()
            .ready()
            .go(url)
    }

    private fun getBundleData() {
        intent.extras?.apply {
            toolbarTitle = getString(TITLE, "")
            url = getString(URL, "")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_webview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_clear -> {
                ActivityUtils.finishActivity(this, true)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (!mAgentWeb!!.back()) {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}