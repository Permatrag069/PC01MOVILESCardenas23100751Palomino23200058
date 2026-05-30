package com.example.pc01movilescardenas23100751palomino23200058

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import java.util.Locale

data class Destination(
    val country: String,
    val city: String,
    val averageCost: Double,
    val imageUrl: String
)

val sampleDestinations = listOf(
    Destination("Francia", "París", 1500.0, "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"),
    Destination("Japón", "Tokio", 2000.0, "https://tipsparatuviaje.com/wp-content/uploads/2019/12/palacio-imperial.jpg"),
    Destination("Perú", "Cusco", 800.0, "https://images.unsplash.com/photo-1526392060635-9d6019884377"),
    Destination("Italia", "Roma", 1200.0, "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),
    Destination("Egipto", "El Cairo", 950.0, "https://images.unsplash.com/photo-1572252009286-268acec5ca0a")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationScreen(navController: NavHostController) {
    val totalDestinations = sampleDestinations.size
    val totalCost = sampleDestinations.sumOf { it.averageCost }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Catálogo de Destinos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sampleDestinations) { destination ->
                DestinationItem(destination)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Cantidad total: $totalDestinations",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Suma acumulada de costos: $${String.format(Locale.US, "%.2f", totalCost)}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DestinationItem(destination: Destination) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = destination.country,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${destination.city} - $${String.format(Locale.US, "%.2f", destination.averageCost)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
