package com.ldl.wanandroidpro.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.base.viewmodel.BaseViewModel
import com.ldl.wanandroidpro.core.model.main.FeedArticleData
import com.ldl.wanandroidpro.viewmodel.main.repository.ArticleRepository

/**
 * 作者：LDL 创建时间：2020/5/21
 * 类说明：
 */
class ArticleViewModel: BaseViewModel() {

    private val mRepository by lazy { ArticleRepository() }

    var refreshing = MutableLiveData<Boolean>()
    var isRefresh = MutableLiveData<Boolean>()

    private var page = 0

    val feedArticleDataList by lazy {
        arrayListOf<FeedArticleData>()
    }

    private fun getFeedArticleList() {
        rxLifeScope.launch({
            val feedArticleListData = mRepository.getFeedArticleList(page)
            if (page == 0) {
                feedArticleDataList.clear()
            }
            isRefresh.value = page == 0
            feedArticleDataList.addAll(feedArticleListData.datas)
        }, {
            LogUtils.e(it.message)
            ToastUtils.showShort(R.string.failed_to_obtain_article_list)
        }, {
            refreshing.value = true
        }, {
            refreshing.value = false
        })
    }

    fun onRefresh() {
        page = 0
        getFeedArticleList()
    }

    fun onLoadMore() {
        page++
        getFeedArticleList()
    }

}