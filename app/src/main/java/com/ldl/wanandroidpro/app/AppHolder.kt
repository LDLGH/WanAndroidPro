package com.ldl.wanandroidpro.app

import android.app.Application
import com.billy.android.loading.Gloading
import com.blankj.utilcode.util.Utils
import com.ldl.wanandroidpro.ui.main.adapter.GlobalAdapter
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.RxHttp
import java.io.File

/**
 * 作者：LDL 创建时间：2020/5/19
 * 类说明：
 */
class AppHolder : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        RxHttp.setDebug(true)
        val cacheDir = File(externalCacheDir, "RxHttpCache")
        //设置最大缓存为10M，缓存有效时长为60秒
        RxHttpPlugins.setCache(
            cacheDir,
            10 * 1024 * 1024,
            CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
        )
        initGloading()
    }

    private fun initGloading() {
        Gloading.initDefault(GlobalAdapter())
    }
}