package com.example.fruitfresh.store.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitfresh.store.data.database.FruitDatabase
import com.example.fruitfresh.store.data.entity.Product
import com.example.fruitfresh.store.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(application: Application) : AndroidViewModel(application) {
    private val database = FruitDatabase.getDatabase(application)
    private val repository = ProductRepository(database.productDao())

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        initializeData()
        loadProducts()
    }

    private fun initializeData() {
        viewModelScope.launch {
            repository.initializeProducts()
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repository.getAllProducts().collect { productList ->
                _products.value = productList
            }
        }
    }

    fun searchProducts(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isEmpty()) {
                repository.getAllProducts().collect { productList ->
                    _products.value = productList
                }
            } else {
                repository.searchProducts(query).collect { productList ->
                    _products.value = productList
                }
            }
        }
    }
}