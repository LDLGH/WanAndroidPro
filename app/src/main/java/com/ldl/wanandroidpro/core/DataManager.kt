package com.ldl.wanandroidpro.core

import com.ldl.wanandroid.core.prefs.PreferenceHelperImpl
import com.ldl.wanandroidpro.core.dao.HistoryData
import com.ldl.wanandroidpro.core.db.DbHelper
import com.ldl.wanandroidpro.core.db.DbHelperImpl
import com.ldl.wanandroidpro.core.prefs.PreferenceHelper

/**
 * 作者：LDL 创建时间：2020/5/27
 * 类说明：
 */
object DataManager : PreferenceHelper, DbHelper {

    private val preferenceHelper = PreferenceHelperImpl()

    private val dbHelper = DbHelperImpl()

    override fun setPassword(password: String) = preferenceHelper.setPassword(password)

    override fun getPassword(): String = preferenceHelper.getPassword()

    override fun setAccount(account: String) = preferenceHelper.setAccount(account)

    override fun getAccount(): String = preferenceHelper.getAccount()

    override fun setLoginStatus(loginStatus: Boolean) = preferenceHelper.setLoginStatus(loginStatus)

    override fun getLoginStatus(): Boolean = preferenceHelper.getLoginStatus()

    override suspend fun addHistoryData(data: String): List<HistoryData> =
        dbHelper.addHistoryData(data)

    override suspend fun clearHistoryData() = dbHelper.clearHistoryData()

    override suspend fun loadAllHistoryData(): List<HistoryData> = dbHelper.loadAllHistoryData()

}