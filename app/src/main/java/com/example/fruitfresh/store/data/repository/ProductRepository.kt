package com.example.fruitfresh.store.data.repository

import com.example.fruitfresh.store.data.dao.ProductDao
import com.example.fruitfresh.store.data.entity.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    suspend fun getProductById(id: Int): Product? = productDao.getProductById(id)

    fun searchProducts(query: String): Flow<List<Product>> = productDao.searchProducts(query)

    suspend fun initializeProducts() {
        if (productDao.getProductCount() == 0) {
            val initialProducts = listOf(
                Product(
                    id = 1,
                    name = "Красное яблоко",
                    price = 120.0,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/15/Red_Apple.jpg",
                    description = "Сочные красные яблоки, богатые витаминами и минералами. Идеальны для здорового питания."
                ),
                Product(
                    id = 2,
                    name = "Банан",
                    price = 80.0,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/1c/Bananas_white_background.jpg",
                    description = "Спелые бананы с нежной мякотью. Отличный источник калия и энергии."
                ),
                Product(
                    id = 3,
                    name = "Виноград",
                    price = 250.0,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/b/bb/Table_grapes_on_white.jpg",
                    description = "Сладкий виноград без косточек. Богат антиоксидантами и витаминами."
                ),
                Product(
                    id = 4,
                    name = "Апельсин",
                    price = 150.0,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Ambersweet_oranges.jpg/800px-Ambersweet_oranges.jpg",
                    description = "Сочные апельсины с ярким вкусом. Высокое содержание витамина C."
                )
            )
            productDao.insertProducts(initialProducts)
        }
    }
}