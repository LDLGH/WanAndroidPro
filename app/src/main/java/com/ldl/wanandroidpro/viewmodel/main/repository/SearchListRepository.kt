package com.ldl.wanandroidpro.viewmodel.main.repository

import com.ldl.wanandroidpro.core.api.Apis
import com.ldl.wanandroidpro.core.model.main.FeedArticleListData
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

/**
 * 作者：LDL 创建时间：2020/6/2
 * 类说明：
 */
class SearchListRepository {

    suspend fun getSearchList(page: Int, k: String): FeedArticleListData =
        RxHttp.postForm(Apis.getSearchList(page))
            .add("k", k)
            .toResponse<FeedArticleListData>()
            .await()

}