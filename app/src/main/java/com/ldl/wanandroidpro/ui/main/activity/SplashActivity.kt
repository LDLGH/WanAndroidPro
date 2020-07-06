package com.ldl.wanandroidpro.ui.main.activity

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ActivityUtils
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.R.layout.activity_splash
import com.ldl.wanandroidpro.base.activity.BaseActivity
import com.ldl.wanandroidpro.databinding.ActivitySplashBinding
import com.ldl.wanandroidpro.viewmodel.main.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 作者：LDL 创建时间：2020/5/20
 * 类说明：
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>(), View.OnClickListener {

    private val viewModel by viewModels<SplashViewModel>()

    override fun getLayoutId(): Int  = activity_splash

    override fun onViewCreated() {
        super.onViewCreated()
        binding.viewModel = viewModel

        view_lottie.setAnimation(R.raw.sheep_play_computer)

        lifecycleScope.launch {
            delay(3000)
            jumpToMain()
        }
        tv_skip.start()
        binding.onClickListener = this
    }

    override fun initToolbar() {

    }

    override fun initData() {
    }

    private fun jumpToMain() {
        ActivityUtils.startActivity(MainActivity::class.java)
        finish()
    }

    override fun onBackPressed() {
    }

    override fun onDestroy() {
        super.onDestroy()
        view_lottie.cancelAnimation()
    }


    override fun onClick(p0: View?) {
        jumpToMain()
    }
}