package com.ldl.wanandroidpro.ui.main.activity

import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.FragmentUtils
import com.jakewharton.rxbinding3.view.clicks
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.R.layout.activity_main
import com.ldl.wanandroidpro.base.activity.BaseActivity
import com.ldl.wanandroidpro.core.DataManager
import com.ldl.wanandroidpro.databinding.ActivityMainBinding
import com.ldl.wanandroidpro.ui.main.fragment.HomepageFragment
import com.ldl.wanandroidpro.util.GlideApp
import com.ldl.wanandroidpro.viewmodel.main.MainViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 作者：LDL 创建时间：2020/5/20
 * 类说明：
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModel<MainViewModel>()

    private val mFragments by lazy {
        arrayListOf<Fragment>()
    }

    private lateinit var mTvUsername: TextView

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                ActivityUtils.startActivity(SearchActivity::class.java)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun getLayoutId(): Int = activity_main

    override fun onViewCreated() {
        super.onViewCreated()
        binding.viewModel = viewModel
        initFragments()
    }

    override fun initToolbar() {
        BarUtils.transparentStatusBar(this)
        setSupportActionBar(toolbar)
        initDrawerLayout()
        initNavigationView()
    }

    override fun initData() {
        observe()
    }

    private fun initFragments() {
        mFragments.add(HomepageFragment())
        FragmentUtils.add(supportFragmentManager, mFragments, R.id.fl_content, 0)
    }

    private fun initDrawerLayout() {
        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.syncState()
        drawerLayout.addDrawerListener(toggle)
    }

    private fun initNavigationView() {
        val headerView = navigation.getHeaderView(0)
        val ivCover = headerView.findViewById<ImageView>(R.id.iv_cover)
        mTvUsername = headerView.findViewById(R.id.tv_username)

        mTvUsername.text =
            if (DataManager.getLoginStatus()) DataManager.getAccount()
            else getString(R.string.login_or_register)

        addSubscribe(ivCover.clicks().subscribe {
            if (!DataManager.getLoginStatus()) {
                ActivityUtils.startActivity(LoginAndRegisterActivity::class.java)
            }
        })

        GlideApp.with(this)
            .load(R.drawable.bg_login)
            .transform(BlurTransformation(6))
            .into(ivCover)

        navigation.menu.findItem(R.id.nav_login)
            .setOnMenuItemClickListener {
                if (DataManager.getLoginStatus()) {
                    showLogoutDialog()
                }
                return@setOnMenuItemClickListener true
            }
        navigation.menu.findItem(R.id.nav_login).isVisible = DataManager.getLoginStatus()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.tips))
            .setMessage(getString(R.string.confirm_logout))
            .setPositiveButton(
                getString(R.string.yes)
            ) { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton(
                getString(R.string.no)
            ) { _, _ ->

            }
            .show()
    }

    private fun observe() {
        viewModel.isLoading.observe(this, Observer {
            if (it) showLoadingDialog() else hideLoadingDialog()
        })
        viewModel.eventMsg.observe(this, Observer {
            mTvUsername.text =
                if (DataManager.getLoginStatus()) DataManager.getAccount()
                else getString(R.string.login_or_register)
            navigation.menu.findItem(R.id.nav_login).isVisible = DataManager.getLoginStatus()
        })
    }
}