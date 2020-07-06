package com.ldl.wanandroidpro.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * 作者：LDL 创建时间：2020/5/18
 * 类说明：
 */
abstract class AbstractSimpleFragment : Fragment() {

    protected lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(getLayoutId(), container, false)
        initDataBinding()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    protected open fun initView() {}

    protected abstract fun initDataBinding()

    protected abstract fun getLayoutId(): Int

    protected abstract fun initData()
}