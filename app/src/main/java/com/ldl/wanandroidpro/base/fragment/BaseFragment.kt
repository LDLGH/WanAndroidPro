package com.ldl.wanandroidpro.base.fragment

import android.app.Activity
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldl.wanandroidpro.base.view.AbstractView

/**
 * 作者：LDL 创建时间：2020/5/18
 * 类说明：
 */
abstract class BaseFragment<DB : ViewDataBinding> : AbstractSimpleFragment(), AbstractView {

    protected lateinit var mActivity: Activity
    protected lateinit var binding: DB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun initDataBinding() {
        binding = DataBindingUtil.bind(mView)!!
        binding.lifecycleOwner = this
    }

    override fun showNormal() {

    }

    override fun showError() {
    }

    override fun showLoading() {
    }

    override fun reload() {
    }

    override fun showLoadingDialog() {
    }

    override fun hideLoadingDialog() {
    }

    override fun showToast(message: String?) = ToastUtils.showShort(message)

    override fun showSnackBar(message: String?) {
        SnackbarUtils.with(mActivity.window.decorView)
            .setMessage(message!!)
            .showSuccess()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}