package com.example.fruitfresh.store.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fruitfresh.store.data.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%'")
    fun searchProducts(query: String): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int
}