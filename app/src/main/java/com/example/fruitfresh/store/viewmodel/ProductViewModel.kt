package com.example.fruitfresh.store.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitfresh.store.data.database.FruitDatabase
import com.example.fruitfresh.store.data.entity.CartItem
import com.example.fruitfresh.store.data.entity.Product
import com.example.fruitfresh.store.data.repository.CartRepository
import com.example.fruitfresh.store.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val database = FruitDatabase.getDatabase(application)
    private val productRepository = ProductRepository(database.productDao())
    private val cartRepository = CartRepository(database.cartDao())

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    private val _quantity = MutableStateFlow(1)
    val quantity: StateFlow<Int> = _quantity.asStateFlow()

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)
        }
    }

    fun updateQuantity(newQuantity: Int) {
        if (newQuantity > 0) {
            _quantity.value = newQuantity
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            _product.value?.let { product ->
                val cartItem = CartItem(
                    productId = product.id,
                    name = product.name,
                    price = product.price,
                    imageUrl = product.imageUrl,
                    quantity = _quantity.value
                )
                cartRepository.addToCart(cartItem)
            }
        }
    }
}