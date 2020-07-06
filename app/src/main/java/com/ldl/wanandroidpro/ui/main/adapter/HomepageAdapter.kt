package com.ldl.wanandroidpro.ui.main.adapter

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.ldl.wanandroidpro.core.model.main.HomepageMultiData

/**
 * 作者：LDL 创建时间：2020/1/7
 * 类说明：
 */
class HomepageAdapter(data: ArrayList<HomepageMultiData>) :
    BaseProviderMultiAdapter<HomepageMultiData>(data) {

    init {
        addItemProvider(ArticleItemProvider())
        addItemProvider(HotSearchItemProvider())
        addItemProvider(UsefulSitesItemProvider())
        addItemProvider(LoginItemProvider())
        addItemProvider(BottomLineProvider())
    }

    override fun getItemType(data: List<HomepageMultiData>, position: Int): Int {
        return data[position].itemType
    }
}