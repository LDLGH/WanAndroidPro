package com.ldl.wanandroidpro.base.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import com.billy.android.loading.Gloading

/**
 * 作者：LDL 创建时间：2020/5/18
 * 类说明：
 */
abstract class BaseRootActivity<DB : ViewDataBinding> : BaseActivity<DB>() {

    private var mHolder: Gloading.Holder? = null

    protected fun initLoadingStatusView(view: View) {
        if (mHolder == null) {
            mHolder = Gloading.getDefault().wrap(view).withRetry {
                reload()
            }
        }
    }

    override fun showNormal() {
        mHolder?.showLoadSuccess()
    }

    override fun showError() {
        mHolder?.showLoadFailed()
    }

    override fun showLoading() {
        mHolder?.showLoading()
    }

}