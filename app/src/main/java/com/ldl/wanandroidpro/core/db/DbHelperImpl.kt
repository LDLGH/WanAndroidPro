package com.ldl.wanandroidpro.core.db

import com.blankj.utilcode.util.Utils
import com.ldl.wanandroidpro.core.dao.AppDatabase
import com.ldl.wanandroidpro.core.dao.HistoryData
import com.ldl.wanandroidpro.core.dao.HistoryDataDao

/**
 * 作者：LDL 创建时间：2019/12/26
 * 类说明：
 */
class DbHelperImpl : DbHelper {

    private lateinit var mHistoryDataList: ArrayList<HistoryData>


    override suspend fun addHistoryData(data: String): ArrayList<HistoryData> {
        mHistoryDataList = ArrayList()
        val allHistoryData = loadAllHistoryData()
        val list = allHistoryData.filter {
            it.key != data
        }
        mHistoryDataList.addAll(list)
        val historyData = HistoryData(date = System.currentTimeMillis(), key = data)
        mHistoryDataList.add(historyData)
        clearHistoryData()
        getHistoryDao().insertAll(mHistoryDataList)
        return mHistoryDataList
    }

    override suspend fun clearHistoryData() {
        getHistoryDao().deleteAll(loadAllHistoryData())
    }

    override suspend fun loadAllHistoryData(): List<HistoryData> = getHistoryDao().getAll()

    private fun getHistoryDao(): HistoryDataDao = AppDatabase.getInstance(Utils.getApp()).userDao()

}