package com.ldl.wanandroidpro.core.api

import rxhttp.wrapper.annotation.DefaultDomain

/**
 * 作者：LDL 创建时间：2020/5/19
 * 类说明：
 */
class Apis {

    companion object {
        @DefaultDomain //设置为默认域名
        const val baseUrl = "https://www.wanandroid.com/"

        /**
         * 获取feed文章列表
         *
         * @param num 页数
         * @return feed文章列表数据
         */
        fun getFeedArticleList(num: Int): String = "article/list/$num/json"

        /**
         * 搜索
         * http://www.wanandroid.com/article/query/0/json
         * @param page page
         * @param k POST search key
         * @return 搜索数据
         */
        fun getSearchList(page: Int) = "article/query/$page/json"

        /**
         * 热搜
         * http://www.wanandroid.com//hotkey/json
         *
         * @return 热门搜索数据
         */
        fun getTopSearchData(): String = "hotkey/json"

        /**
         * 广告栏
         * http://www.wanandroid.com/banner/json
         *
         * @return 广告栏数据
         */
        fun getBannerData(): String = "banner/json"

        /**
         * 常用网站
         * http://www.wanandroid.com/friend/json
         *
         * @return 常用网站数据
         */
        fun getUsefulSites(): String = "friend/json"

        /**
         * 登陆
         * http://www.wanandroid.com/user/login
         */
        fun getLoginData(): String = "user/login"

        /**
         * 注册
         * http://www.wanandroid.com/user/register
         */
        fun getRegisterData(): String = "user/register"

        /**
         * 退出登录
         * http://www.wanandroid.com/user/logout/json
         */
        fun logout(): String = "user/logout/json"

    }


}