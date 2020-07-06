package com.ldl.wanandroidpro.core.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 作者：LDL 创建时间：2020/5/28
 * 类说明：
 */
@Entity(tableName = "table_history")
data class HistoryData(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "date") var date: Long,
    @ColumnInfo(name = "key") var key: String
)