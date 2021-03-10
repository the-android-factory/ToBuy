package com.dmp.tobuy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dmp.tobuy.database.dao.ItemEntityDao
import com.dmp.tobuy.database.entity.ItemEntity

@Database(
        entities = [ItemEntity::class],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (appDatabase != null) {
                return appDatabase!!
            }

            appDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "to-buy-database").build()
            return appDatabase!!
        }
    }

    abstract fun itemEntityDao(): ItemEntityDao
}