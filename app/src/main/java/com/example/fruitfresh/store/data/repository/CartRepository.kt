package com.example.fruitfresh.store.data.repository

import com.example.fruitfresh.store.data.dao.CartDao
import com.example.fruitfresh.store.data.entity.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    fun getAllCartItems(): Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun getCartItem(productId: Int): CartItem? = cartDao.getCartItem(productId)

    suspend fun addToCart(cartItem: CartItem) {
        val existingItem = cartDao.getCartItem(cartItem.productId)
        if (existingItem != null) {
            cartDao.updateCartItem(existingItem.copy(quantity = existingItem.quantity + cartItem.quantity))
        } else {
            cartDao.insertCartItem(cartItem)
        }
    }

    suspend fun updateCartItem(cartItem: CartItem) = cartDao.updateCartItem(cartItem)

    suspend fun removeFromCart(cartItem: CartItem) = cartDao.deleteCartItem(cartItem)

    suspend fun clearCart() = cartDao.clearCart()

    fun getCartItemCount(): Flow<Int?> = cartDao.getCartItemCount()
}