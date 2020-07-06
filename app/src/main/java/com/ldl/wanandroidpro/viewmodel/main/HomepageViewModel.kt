package com.ldl.wanandroidpro.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.blankj.rxbus.RxBus
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldl.wanandroidpro.R
import com.ldl.wanandroidpro.base.viewmodel.BaseViewModel
import com.ldl.wanandroidpro.core.DataManager
import com.ldl.wanandroidpro.core.model.event.EventMsg
import com.ldl.wanandroidpro.core.model.main.*
import com.ldl.wanandroidpro.util.RxBusManager
import com.ldl.wanandroidpro.viewmodel.main.repository.HomepageRepository

/**
 * 作者：LDL 创建时间：2020/5/25
 * 类说明：
 */
class HomepageViewModel : BaseViewModel() {

    private val mRepository by lazy { HomepageRepository() }

    private val feedArticleDataList by lazy {
        arrayListOf<FeedArticleData>()
    }
    var bannerData: List<BannerData>? = null
    var topSearchData: List<TopSearchData>? = null
    var usefulSiteData: List<UsefulSiteData>? = null

    var refreshing = MutableLiveData<Boolean>()
    var showLoading = MutableLiveData<Int>()

    val homepageMultiData by lazy { arrayListOf<HomepageMultiData>() }

    var eventMsg = MutableLiveData<String>()

    init {
        RxBusManager.subscribe(this, object : RxBus.Callback<EventMsg>() {
            override fun onEvent(t: EventMsg?) {
                if (t?.code == EventMsg.LOGIN) {
                    eventMsg.value = t.msg
                }
                if (t?.msg == "logout") {
                    val loginData = HomepageMultiData(
                        HomepageMultiData.LOGIN,
                        StringUtils.getString(R.string.login_immediately),
                        StringUtils.getString(R.string.login_collect_articles),
                        ""
                    )
                    homepageMultiData.add(3, loginData)
                } else {
                    homepageMultiData.removeAt(3)
                }
            }
        })
    }

    fun getAllData(isShowLoading: Boolean = true) {
        rxLifeScope.launch({
            mRepository.getAllData()

            mRepository.feedArticleListData?.also {
                feedArticleDataList.clear()
                feedArticleDataList.addAll(it.datas)
            }
            topSearchData = mRepository.topSearchData
            usefulSiteData = mRepository.usefulSiteData
            bannerData = mRepository.bannerData

            val articleData = HomepageMultiData(
                HomepageMultiData.ARTICLE,
                StringUtils.getString(R.string.recommended_article),
                StringUtils.getString(R.string.carefully_selected_for_you),
                GsonUtils.toJson(feedArticleDataList)
            )
            val hotData = HomepageMultiData(
                HomepageMultiData.HOT_SEARCH,
                StringUtils.getString(R.string.hot_search),
                StringUtils.getString(R.string.discover_more),
                GsonUtils.toJson(topSearchData)
            )

            val usefulData = HomepageMultiData(
                HomepageMultiData.USEFUL_SITES,
                StringUtils.getString(R.string.useful_sites),
                StringUtils.getString(R.string.website_collection),
                GsonUtils.toJson(usefulSiteData)
            )

            homepageMultiData.clear()
            homepageMultiData.add(articleData)
            homepageMultiData.add(hotData)
            homepageMultiData.add(usefulData)

            if(!DataManager.getLoginStatus()){
                val loginData = HomepageMultiData(
                    HomepageMultiData.LOGIN,
                    StringUtils.getString(R.string.login_immediately),
                    StringUtils.getString(R.string.login_collect_articles),
                    ""
                )
                homepageMultiData.add(loginData)
            }

            val bottomLineData = HomepageMultiData(
                HomepageMultiData.BOTTOM_LINE, "", "", ""
            )
            homepageMultiData.add(bottomLineData)

            if (isShowLoading) {
                showLoading.value = SHOW_NORMAL
            }
        }, {
            LogUtils.e(it.message)
            ToastUtils.showShort(R.string.failed_to_obtain_article_list)
            if (isShowLoading) {
                showLoading.value = SHOW_ERROR
            }
        }, {
            if (isShowLoading) {
                showLoading.value = SHOW_LOADING
            } else {
                refreshing.value = true
            }
        }, {
            if (!isShowLoading) {
                refreshing.value = false
            }
        })
    }

    fun onRefresh() {
        getAllData(false)
    }

    fun getMenuList(): ArrayList<MenuData> {
        val menuList: ArrayList<MenuData> by lazy { ArrayList<MenuData>() }
        menuList.add(MenuData("文章", R.mipmap.ic_article))
        menuList.add(MenuData("导航", R.mipmap.ic_navigation))
        menuList.add(MenuData("知识体系", R.mipmap.ic_knowledge))
        menuList.add(MenuData("公众号", R.mipmap.ic_wx))
        menuList.add(MenuData("项目", R.mipmap.ic_project))
        return menuList
    }


    override fun onCleared() {
        super.onCleared()
        RxBusManager.unregister(this)
    }

    companion object {

        const val SHOW_LOADING = 0

        const val SHOW_NORMAL = 1

        const val SHOW_ERROR = 2

    }
}