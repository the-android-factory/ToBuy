package com.dmp.tobuy.ui.home

import com.dmp.tobuy.database.entity.ItemEntity

interface ItemEntityInterface {
    fun onDeleteItemEntity(itemEntity: ItemEntity)
    fun onBumpPriority(itemEntity: ItemEntity)
}