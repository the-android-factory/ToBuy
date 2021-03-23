package com.dmp.tobuy.arch

import com.dmp.tobuy.database.AppDatabase
import com.dmp.tobuy.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun updateItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().update(itemEntity)
    }

    fun getAllItems(): Flow<List<ItemEntity>> {
        return appDatabase.itemEntityDao().getAllItemEntities()
    }
}