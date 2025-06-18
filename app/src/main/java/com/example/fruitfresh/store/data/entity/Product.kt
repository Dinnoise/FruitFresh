package com.example.fruitfresh.store.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val category: String = "Фрукты"
)