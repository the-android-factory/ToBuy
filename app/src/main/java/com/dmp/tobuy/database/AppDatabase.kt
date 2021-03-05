package com.dmp.tobuy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmp.tobuy.database.dao.ItemEntityDao
import com.dmp.tobuy.database.entity.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun itemEntityDao(): ItemEntityDao
}