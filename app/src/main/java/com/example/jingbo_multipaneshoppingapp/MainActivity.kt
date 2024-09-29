package com.example.jingbo_multipaneshoppingapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment

import com.example.jingbo_multipaneshoppingapp.ui.theme.JingboMultipaneShoppingAppTheme


data class Product(
    val name: String,
    val price: String,
    val description: String
)

val products = listOf(
    Product("Product A", "$100", "This is a great product A."),
    Product("Product B", "$150", "This is product B with more features."),
    Product("Product C", "$200", "Premium product C."),
    Product("Product D", "$250", "This is product D with a lot of features."),
    Product("Product E", "$300", "This is my favorite product E .")
)



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JingboMultipaneShoppingAppTheme {
                ShoppingApp()
            }
        }
    }
}

@Composable
fun ShoppingApp() {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        // Dual pane layout
        Row(Modifier.fillMaxSize()) {
            ProductList(
                products = products,
                onProductSelected = { selectedProduct = it },
                modifier = Modifier.weight(1f)
            )

            ProductDetails(
                selectedProduct = selectedProduct,
                onSwipeBack = { selectedProduct = null },
                modifier = Modifier.weight(1f)
            )
        }
    } else {
        Crossfade(targetState = selectedProduct, label = "") { product ->
            if (selectedProduct == null) {
                ProductList(products = products, onProductSelected = { selectedProduct = it })
            } else {
                ProductDetails(selectedProduct = selectedProduct, onSwipeBack = { selectedProduct = null })
            }
        }
    }
}


@Composable
fun ProductList(products: List<Product>, onProductSelected: (Product) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        items(products) { product ->
            ProductItem(product = product, onProductSelected = onProductSelected)
        }
    }
}

@Composable
fun ProductItem(product: Product, onProductSelected: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onProductSelected(product) },
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Placeholder for product image
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ProductDetails(selectedProduct: Product?, onSwipeBack: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount > 0) {
                        onSwipeBack()
                    }
                    change.consume()
                }
            }
    ) {
        if (selectedProduct != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = selectedProduct.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = selectedProduct.price,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = selectedProduct.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Select a product to view details.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JingboMultipaneShoppingAppTheme {
        ShoppingApp()
    }
}