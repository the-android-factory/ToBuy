package com.dmp.tobuy.ui.customization

import com.dmp.tobuy.database.entity.CategoryEntity

interface CategoryEntityInterface {

    fun onCategoryEmptyStateClicked()
    fun onDeleteCategory(categoryEntity: CategoryEntity)
    fun onCategorySelected(categoryEntity: CategoryEntity)
}