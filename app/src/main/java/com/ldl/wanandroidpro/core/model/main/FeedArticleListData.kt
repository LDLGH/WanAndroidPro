package com.ldl.wanandroidpro.core.model.main

/**
 * 作者：LDL 创建时间：2019/12/27
 * 类说明：
 */
data class FeedArticleListData(
    var curPage: Int,
    var datas: ArrayList<FeedArticleData>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)