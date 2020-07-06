package com.ldl.wanandroidpro.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * 作者：LDL 创建时间：2020/5/18
 * 类说明：
 */
abstract class AbstractSimpleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLayoutId()
        onViewCreated()
        initToolbar()
        initData()
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    /**
     * 在initData之前执行
     */
    protected abstract fun onViewCreated()

    /**
     * 初始化ToolBar
     */
    protected abstract fun initToolbar()

    /**
     * 初始化数据
     */
    protected abstract fun initData()
}