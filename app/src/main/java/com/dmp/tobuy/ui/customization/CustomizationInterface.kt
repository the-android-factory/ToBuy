package com.dmp.tobuy.ui.customization

import com.dmp.tobuy.database.entity.CategoryEntity

interface CustomizationInterface {

    fun onCategoryEmptyStateClicked()
    fun onDeleteCategory(categoryEntity: CategoryEntity)
    fun onCategorySelected(categoryEntity: CategoryEntity)
    fun onPrioritySelected(priorityName: String)
}