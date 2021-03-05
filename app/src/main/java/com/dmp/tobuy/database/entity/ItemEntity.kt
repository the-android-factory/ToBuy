package com.dmp.tobuy.database.entity

import androidx.room.Entity

@Entity(tableName = "item_entity")
data class ItemEntity(
    val id: String = "",
    val title: String = "",
    val description: String? = null,
    val priority: Int = 0,
    val createdAt: Long = 0L,
    val categoryId: String = ""
)