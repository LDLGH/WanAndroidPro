package com.ldl.wanandroidpro.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.blankj.utilcode.util.ToastUtils
import com.ldl.wanandroidpro.base.viewmodel.BaseViewModel
import com.ldl.wanandroidpro.core.DataManager
import com.ldl.wanandroidpro.core.dao.HistoryData
import com.ldl.wanandroidpro.core.model.main.TopSearchData
import com.ldl.wanandroidpro.viewmodel.main.repository.SearchRepository

/**
 * 作者：LDL 创建时间：2020/5/28
 * 类说明：
 */
class SearchViewModel : BaseViewModel() {

    private val mRepository by lazy { SearchRepository() }

    var topSearchData = MutableLiveData<List<TopSearchData>>()
    var historyData = MutableLiveData<List<HistoryData>>()

    val topSearchDataList by lazy {
        arrayListOf<TopSearchData>()
    }

    fun getTopSearchData() {
        rxLifeScope.launch({
            val list = mRepository.getTopSearchData()
            topSearchData.value = list
            topSearchDataList.addAll(list)
        }, {
            ToastUtils.showShort(it.message)
        })
    }

    fun addHistoryData(data: String) {
        rxLifeScope.launch({
            DataManager.addHistoryData(data)
            loadAllHistoryData()
        }, {
            ToastUtils.showShort(it.message)
        })
    }

    fun clearHistoryData() {
        rxLifeScope.launch({
            DataManager.clearHistoryData()
        }, {
            ToastUtils.showShort(it.message)
        })
    }

    fun loadAllHistoryData() {
        rxLifeScope.launch({
            val list = DataManager.loadAllHistoryData()
            historyData.value = list
        }, {
            ToastUtils.showShort(it.message)
        })
    }

}