package com.ldl.wanandroidpro.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.base.viewmodel.BaseViewModel
import com.ldl.wanandroidpro.core.model.main.FeedArticleData
import com.ldl.wanandroidpro.viewmodel.main.repository.SearchListRepository

/**
 * 作者：LDL 创建时间：2020/6/2
 * 类说明：
 */
class SearchListViewModel : BaseViewModel() {

    private val mRepository by lazy { SearchListRepository() }

    var refreshing = MutableLiveData<Boolean>()
    var isRefresh = MutableLiveData<Boolean>()

    private var page = 0
    var k = ""

    val feedArticleDataList by lazy {
        arrayListOf<FeedArticleData>()
    }

    private fun getFeedArticleList() {
        rxLifeScope.launch({
            val feedArticleListData = mRepository.getSearchList(page, k)
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