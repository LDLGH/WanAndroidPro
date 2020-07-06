package com.ldl.wanandroidpro.ui.main.activity

import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ObjectUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.R.layout.activity_login_register
import com.ldl.wanandroidpro.base.activity.BaseActivity
import com.ldl.wanandroidpro.databinding.ActivityLoginRegisterBinding
import com.ldl.wanandroidpro.viewmodel.main.LoginAndRegisterViewModel
import kotlinx.android.synthetic.main.activity_login_register.*
import java.util.concurrent.TimeUnit

/**
 * 作者：LDL 创建时间：2020/5/27
 * 类说明：
 */
class LoginAndRegisterActivity : BaseActivity<ActivityLoginRegisterBinding>() {

    private val viewModel by viewModels<LoginAndRegisterViewModel>()

    private var isLogin = true

    override fun onBackPressed() {
        KeyboardUtils.hideSoftInput(this)
        if (cl_head.isVisible) {
            cl_head.visibility = View.GONE
            cl_content.visibility = View.VISIBLE
            toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int  = activity_login_register

    override fun onViewCreated() {
        super.onViewCreated()
        binding.viewModel = viewModel

        addSubscribe(btn_login.clicks()
            .subscribe {
                isLogin = true
                onViewData()
            })

        addSubscribe(tv_create_account.clicks()
            .subscribe {
                isLogin = false
                onViewData()
            })
        addSubscribe(btn_submit.clicks()
            .throttleFirst(2, TimeUnit.SECONDS)
            .subscribe {
                val username = et_user.text.toString()
                val password = et_password.text.toString()
                if (ObjectUtils.isEmpty(et_user.text.toString())) {
                    textInputLayoutUser.error = getString(R.string.input_username)
                    return@subscribe
                }
                if (et_password.text.toString().length < 6) {
                    textInputLayoutPassword.error = getString(R.string.password_limit)
                    return@subscribe
                }
                KeyboardUtils.hideSoftInput(this)
                if (isLogin) {
                    viewModel.login(username, password)
                } else {
                    viewModel.registerAndLogin(username, password)
                }
            })

        addSubscribe(et_user.textChanges()
            .subscribe {
                textInputLayoutUser.isErrorEnabled = false
            })

        addSubscribe(et_password.textChanges()
            .subscribe {
                textInputLayoutPassword.isErrorEnabled = false
            })
    }

    override fun initToolbar() {
        BarUtils.transparentStatusBar(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initData() {
        observe()
    }

    private fun onViewData() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        cl_head.visibility = View.VISIBLE
        cl_content.visibility = View.GONE
        et_user.requestFocus()
        KeyboardUtils.showSoftInput(this)
        binding.title = getString(if (isLogin) R.string.login else R.string.register_account)
    }

    private fun observe() {
        viewModel.isLogin.observe(this, Observer {
            if (it) {
                showToast(getString(R.string.login_success))
                finish()
            }
        })
        viewModel.isRegisterAndLogin.observe(this, Observer {
            if (it) {
                showToast(getString(R.string.login_success))
                finish()
            }
        })
        viewModel.isLoading.observe(this, Observer {
            if (it) showLoadingDialog() else hideLoadingDialog()
        })
    }


}