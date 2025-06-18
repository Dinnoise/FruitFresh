package com.example.fruitfresh.store.ui.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.fruitfresh.store.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productId: Int,
    onBackClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: ProductViewModel = viewModel()
    val product by viewModel.product.collectAsState()
    val quantity by viewModel.quantity.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "Детали товара",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        product?.let { prod ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                // Product Image
                AsyncImage(
                    model = prod.imageUrl,
                    contentDescription = prod.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Product Info
                Text(
                    text = prod.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )

                Text(
                    text = prod.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "${prod.price.toInt()} ₽/кг",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Quantity Selection
                Text(
                    text = "Количество (кг)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.updateQuantity(quantity - 1) },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = quantity.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    IconButton(
                        onClick = { viewModel.updateQuantity(quantity + 1) },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Description
                Text(
                    text = "Описание",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = prod.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(32.dp))
            }

            // Add to Cart Button
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Итого:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "${(prod.price * quantity).toInt()} ₽",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.addToCart()
                            onAddToCart()
                        },
                        modifier = Modifier
                            .height(56.dp)
                            .width(200.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "В корзину",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}