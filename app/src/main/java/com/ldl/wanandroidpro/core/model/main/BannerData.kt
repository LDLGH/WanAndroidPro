package com.ldl.wanandroidpro.core.model.main

/**
 * 作者：LDL 创建时间：2020/1/2
 * 类说明：
 */
data class BannerData(
    var id: Int,
    var url: String,
    var imagePath: String,
    var title: String,
    var desc: String,
    var isVisible: Int,
    var order: Int,
    var type: Int
)