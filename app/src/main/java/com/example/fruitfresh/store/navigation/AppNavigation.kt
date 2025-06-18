package com.example.fruitfresh.store.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruitfresh.store.ui.components.BottomNavigationBar
import com.example.fruitfresh.store.ui.screens.cart.CartScreen
import com.example.fruitfresh.store.ui.screens.catalog.CatalogScreen
import com.example.fruitfresh.store.ui.screens.menu.MenuScreen
import com.example.fruitfresh.store.ui.screens.product.ProductScreen
import com.example.fruitfresh.store.ui.screens.profile.ProfileScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "catalog",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("catalog") {
                CatalogScreen(
                    onProductClick = { productId ->
                        navController.navigate("product/$productId")
                    }
                )
            }
            composable("product/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 1
                ProductScreen(
                    productId = productId,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { navController.popBackStack() }
                )
            }
            composable("cart") {
                CartScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
            composable("menu") {
                MenuScreen(
                    onNavigateTo = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}