package com.example.fruitfresh.store.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.fruitfresh.store.data.dao.CartDao
import com.example.fruitfresh.store.data.dao.ProductDao
import com.example.fruitfresh.store.data.entity.CartItem
import com.example.fruitfresh.store.data.entity.Product

@Database(
    entities = [Product::class, CartItem::class],
    version = 1,
    exportSchema = false
)
abstract class FruitDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: FruitDatabase? = null

        fun getDatabase(context: Context): FruitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FruitDatabase::class.java,
                    "fruit_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}