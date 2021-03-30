package com.dmp.tobuy.ui.home

import com.dmp.tobuy.database.entity.ItemEntity

interface ItemEntityInterface {
    fun onBumpPriority(itemEntity: ItemEntity)
    fun onItemSelected(itemEntity: ItemEntity)
}