package com.dmp.tobuy.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_entity")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String
)