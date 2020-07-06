package com.ldl.wanandroidpro.core.db

import com.ldl.wanandroidpro.core.dao.HistoryData

/**
 * 作者：LDL 创建时间：2019/12/26
 * 类说明：
 */
interface DbHelper {

    /**
     * 增加历史数据
     */
    suspend fun addHistoryData(data: String): List<HistoryData>

    /**
     * Clear search history data
     */
    suspend fun clearHistoryData()

    /**
     * Load all history data
     */
    suspend fun loadAllHistoryData(): List<HistoryData>

}