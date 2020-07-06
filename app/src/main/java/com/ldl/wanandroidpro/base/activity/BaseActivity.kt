package com.ldl.wanandroidpro.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldl.wanandroidpro.base.view.AbstractView
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 作者：LDL 创建时间：2020/5/18
 * 类说明：
 */
abstract class BaseActivity<DB : ViewDataBinding> : AbstractSimpleActivity(), AbstractView {

    protected lateinit var binding: DB
    private var loadView: BasePopupView? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun onViewCreated() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
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
        if (loadView == null) {
            loadView = XPopup.Builder(this)
                .asLoading()
        }
        loadView?.apply {
            if (!isShow) {
                show()
            }
        }
    }

    override fun hideLoadingDialog() {
        loadView?.dismiss()
    }

    override fun showToast(message: String?) = ToastUtils.showShort(message)

    override fun showSnackBar(message: String?) {
        SnackbarUtils.with(window.decorView)
            .setMessage(message!!)
            .showSuccess()
    }


    protected fun addSubscribe(disposable: Disposable?) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
        binding.unbind()
    }
}