package com.example.fruitfresh.store.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitfresh.store.data.database.FruitDatabase
import com.example.fruitfresh.store.data.entity.CartItem
import com.example.fruitfresh.store.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val database = FruitDatabase.getDatabase(application)
    private val repository = CartRepository(database.cartDao())

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val cartItemCount = repository.getCartItemCount()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            repository.getAllCartItems().collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity > 0) {
                repository.updateCartItem(cartItem.copy(quantity = newQuantity))
            } else {
                repository.removeFromCart(cartItem)
            }
        }
    }

    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch {
            repository.removeFromCart(cartItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
}