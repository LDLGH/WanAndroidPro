package com.ldl.wanandroidpro.core.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ldl.wanandroidpro.app.Constants

/**
 * 作者：LDL 创建时间：2020/3/20
 * 类说明：
 */
@Database(entities = [HistoryData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): HistoryDataDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.DB_NAME //数据库名称
                ).allowMainThreadQueries().build()
            }
            return instance as AppDatabase
        }
    }

}