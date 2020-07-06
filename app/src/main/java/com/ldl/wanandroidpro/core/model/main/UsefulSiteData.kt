package com.ldl.wanandroidpro.core.model.main

import java.io.Serializable

/**
 * 作者：LDL 创建时间：2020/1/7
 * 类说明：
 */
data class UsefulSiteData(
    var id: Int,
    var name: String,
    var link: String,
    var visible: Int,
    var order: Int,
    var icon: String
) : Serializable