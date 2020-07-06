package com.ldl.wanandroidpro.viewmodel.main.repository

import com.ldl.wanandroidpro.core.api.Apis
import com.ldl.wanandroidpro.core.model.main.BannerData
import com.ldl.wanandroidpro.core.model.main.FeedArticleListData
import com.ldl.wanandroidpro.core.model.main.TopSearchData
import com.ldl.wanandroidpro.core.model.main.UsefulSiteData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import rxhttp.async
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

/**
 * 作者：LDL 创建时间：2020/5/25
 * 类说明：
 */
class HomepageRepository : CoroutineScope by MainScope() {

    var bannerData: List<BannerData>? = null
    var feedArticleListData: FeedArticleListData? = null
    var topSearchData: List<TopSearchData>? = null
    var usefulSiteData: List<UsefulSiteData>? = null

    suspend fun getAllData() {
        val asyncBanner = RxHttp.get(Apis.getBannerData())
            .toResponse<List<BannerData>>()
            .async(this)
        val asyncFeedArticle = RxHttp.get(Apis.getFeedArticleList(0))
            .toResponse<FeedArticleListData>()
            .async(this)
        val asyncTopSearch = RxHttp.get(Apis.getTopSearchData())
            .toResponse<List<TopSearchData>>()
            .async(this)
        val asyncUsefulSites = RxHttp.get(Apis.getUsefulSites())
            .toResponse<List<UsefulSiteData>>()
            .async(this)

        bannerData = asyncBanner.await()
        feedArticleListData = asyncFeedArticle.await()
        topSearchData = asyncTopSearch.await()
        usefulSiteData = asyncUsefulSites.await()
    }

}