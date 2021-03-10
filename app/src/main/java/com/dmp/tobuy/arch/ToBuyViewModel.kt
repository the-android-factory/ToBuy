package com.dmp.tobuy.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmp.tobuy.database.AppDatabase
import com.dmp.tobuy.database.entity.ItemEntity

class ToBuyViewModel: ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()

    fun init(appDatabase: AppDatabase) {
        repository = ToBuyRepository(appDatabase)

        val items = repository.getAllItems()
        itemEntitiesLiveData.postValue(items)
    }

    fun insertItem(itemEntity: ItemEntity) {
        repository.insertItem(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) {
        repository.deleteItem(itemEntity)
    }
}