package com.dmp.tobuy.arch

import com.dmp.tobuy.database.AppDatabase
import com.dmp.tobuy.database.entity.CategoryEntity
import com.dmp.tobuy.database.entity.ItemEntity
import com.dmp.tobuy.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {

    // region ItemEntity
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

    fun getAllItemWithCategoryEntities(): Flow<List<ItemWithCategoryEntity>> {
        return appDatabase.itemEntityDao().getAllItemWithCategoryEntities()
    }
    // endregion ItemEntity

    // region CategoryEntity
    suspend fun insertCategory(categoryEntity: CategoryEntity) {
        appDatabase.categoryEntityDao().insert(categoryEntity)
    }

    suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        appDatabase.categoryEntityDao().delete(categoryEntity)
    }

    suspend fun updateCategory(categoryEntity: CategoryEntity) {
        appDatabase.categoryEntityDao().update(categoryEntity)
    }

    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return appDatabase.categoryEntityDao().getAllCategoryEntities()
    }
    // endregion CategoryEntity
}