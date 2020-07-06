package com.ldl.wanandroidpro.base.view

/**
 * 作者：LDL 创建时间：2020/5/18
 * 类说明：
 */
interface AbstractView {

    fun showNormal()

    fun showError()

    fun showLoading()

    fun reload()

    fun showLoadingDialog()

    fun hideLoadingDialog()

    fun showToast(message: String?)

    fun showSnackBar(message: String?)

}