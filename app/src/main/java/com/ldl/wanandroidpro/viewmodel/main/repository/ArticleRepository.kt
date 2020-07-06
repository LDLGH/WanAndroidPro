package com.ldl.wanandroidpro.viewmodel.main.repository

import com.ldl.wanandroidpro.core.api.Apis
import com.ldl.wanandroidpro.core.model.main.FeedArticleListData
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

/**
 * 作者：LDL 创建时间：2020/5/22
 * 类说明：
 */
class ArticleRepository {

    suspend fun getFeedArticleList(num: Int): FeedArticleListData =
        RxHttp.get(Apis.getFeedArticleList(num))
            .toResponse<FeedArticleListData>()
            .await()
}