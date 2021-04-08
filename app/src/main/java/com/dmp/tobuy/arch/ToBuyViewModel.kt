package com.dmp.tobuy.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmp.tobuy.database.AppDatabase
import com.dmp.tobuy.database.entity.CategoryEntity
import com.dmp.tobuy.database.entity.ItemEntity
import com.dmp.tobuy.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ToBuyViewModel : ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()
    val itemWithCategoryEntitiesLiveData = MutableLiveData<List<ItemWithCategoryEntity>>()
    val categoryEntitiesLiveData = MutableLiveData<List<CategoryEntity>>()

    val transactionCompleteLiveData = MutableLiveData<Event<Boolean>>()

    // Home page
    var currentSort: HomeViewState.Sort = HomeViewState.Sort.NONE
    private val _homeViewStateLiveData = MutableLiveData<HomeViewState>()
    val homeViewStateLiveData: LiveData<HomeViewState>
        get() = _homeViewStateLiveData

    // Categories in the Add/Update screen
    private val _categoriesViewStateLiveData = MutableLiveData<CategoriesViewState>()
    val categoriesViewStateLiveData: LiveData<CategoriesViewState>
        get() = _categoriesViewStateLiveData

    fun init(appDatabase: AppDatabase) {
        repository = ToBuyRepository(appDatabase)

        // Initialize our Flow connectivity to the DB
        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items)
            }
        }

        viewModelScope.launch {
            repository.getAllItemWithCategoryEntities().collect { items ->
                itemWithCategoryEntitiesLiveData.postValue(items)

                updateHomeViewState(items)
            }
        }

        viewModelScope.launch {
            repository.getAllCategories().collect { categories ->
                categoryEntitiesLiveData.postValue(categories)
            }
        }
    }

    private fun updateHomeViewState(items: List<ItemWithCategoryEntity>) {

        val dataList = ArrayList<HomeViewState.DataItem<*>>()
        when (currentSort) {
            HomeViewState.Sort.NONE -> {
                var currentPriority: Int = -1
                items.sortedByDescending {
                    it.itemEntity.priority
                }.forEach { item ->
                    if (item.itemEntity.priority != currentPriority) {
                        currentPriority = item.itemEntity.priority
                        val headerItem = HomeViewState.DataItem(
                            data = getHeaderTextForPriority(currentPriority),
                            isHeader = true
                        )

                        dataList.add(headerItem)
                    }

                    val dataItem = HomeViewState.DataItem(data = item)
                    dataList.add(dataItem)
                }
            }
            HomeViewState.Sort.CATEGORY -> {
                // implement me
            }
            HomeViewState.Sort.OLDEST -> {
                // implement me
            }
            HomeViewState.Sort.NEWEST -> {
                // implement me
            }
        }

        _homeViewStateLiveData.postValue(
            HomeViewState(
                dataList = dataList,
                isLoading = false,
                sort = currentSort
            )
        )
    }

    private fun getHeaderTextForPriority(priority: Int): String {
        return when (priority) {
            1 -> "Low"
            2 -> "Medium"
            else -> "High"
        }
    }

    data class HomeViewState(
        val dataList: List<DataItem<*>> = emptyList(),
        val isLoading: Boolean = false,
        val sort: Sort = Sort.NONE
    ) {
        data class DataItem<T>(
            val data: T,
            val isHeader: Boolean = false
        )

        enum class Sort(val displayName: String) {
            NONE("None"),
            CATEGORY("Category"),
            OLDEST("Oldest"),
            NEWEST("Newest")
        }
    }

    // region Category
    fun onCategorySelected(categoryId: String, showLoading: Boolean = false) {
        if (showLoading) {
            val loadingViewState = CategoriesViewState(isLoading = true)
            _categoriesViewStateLiveData.value = loadingViewState
        }

        val categories = categoryEntitiesLiveData.value ?: return
        val viewStateItemList = ArrayList<CategoriesViewState.Item>()

        // Default category (un-selecting a category)
        viewStateItemList.add(
            CategoriesViewState.Item(
                categoryEntity = CategoryEntity.getDefaultCategory(),
                isSelected = categoryId == CategoryEntity.DEFAULT_CATEGORY_ID
            )
        )

        // Populate the rest of the list with what we have in the DB
        categories.forEach {
            viewStateItemList.add(
                CategoriesViewState.Item(
                    categoryEntity = it,
                    isSelected = it.id == categoryId
                )
            )
        }

        val viewState = CategoriesViewState(itemList = viewStateItemList)
        _categoriesViewStateLiveData.postValue(viewState)
    }

    data class CategoriesViewState(
        val isLoading: Boolean = false,
        val itemList: List<Item> = emptyList()
    ) {
        data class Item(
            val categoryEntity: CategoryEntity = CategoryEntity(),
            val isSelected: Boolean = false
        )

        fun getSelectedCategoryId(): String {
            return itemList.find { it.isSelected }?.categoryEntity?.id
                ?: CategoryEntity.DEFAULT_CATEGORY_ID
        }
    }
    // endregion Category

    // region ItemEntity
    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }

    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }
    // endregion CategoryEntity

    // region CategoryEntity
    fun insertCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(categoryEntity)
        }
    }

    fun updateCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.updateCategory(categoryEntity)

            transactionCompleteLiveData.postValue(Event(true))
        }
    }
    // endregion CategoryEntity
}