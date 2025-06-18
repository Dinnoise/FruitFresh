package com.example.fruitfresh.store.ui.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class MenuItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val route: String? = null,
    val onClick: (() -> Unit)? = null
)

@Composable
fun MenuScreen(
    onNavigateTo: (String) -> Unit
) {
    val menuItems = listOf(
        MenuItem(
            icon = Icons.Default.Home,
            title = "Каталог",
            subtitle = "Просмотр всех фруктов",
            route = "catalog"
        ),
        MenuItem(
            icon = Icons.Default.ShoppingCart,
            title = "Корзина",
            subtitle = "Мои покупки",
            route = "cart"
        ),
        MenuItem(
            icon = Icons.Default.Person,
            title = "Профиль",
            subtitle = "Личная информация",
            route = "profile"
        ),
        MenuItem(
            icon = Icons.Default.History,
            title = "История заказов",
            subtitle = "Предыдущие покупки"
        ),
        MenuItem(
            icon = Icons.Default.LocationOn,
            title = "Адреса доставки",
            subtitle = "Управление адресами"
        ),
        MenuItem(
            icon = Icons.Default.CreditCard,
            title = "Способы оплаты",
            subtitle = "Банковские карты"
        ),
        MenuItem(
            icon = Icons.Default.Info,
            title = "О приложении",
            subtitle = "FruitFresh v1.0.0"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Меню",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "FruitFresh",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Menu Items
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(menuItems) { item ->
                MenuItemCard(
                    item = item,
                    onClick = {
                        item.route?.let { route ->
                            onNavigateTo(route)
                        } ?: item.onClick?.invoke()
                    }
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(
    item: MenuItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )

                item.subtitle?.let { subtitle ->
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Arrow",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}