package com.ldl.wanandroidpro.core.dao

import androidx.room.*

/**
 * 作者：LDL 创建时间：2020/5/28
 * 类说明：
 */
@Dao
abstract class HistoryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(list: List<HistoryData>)

    @Query("select * from table_history")
    abstract suspend fun getAll(): List<HistoryData>

    @Delete
    abstract suspend fun deleteAll(list: List<HistoryData>)

}