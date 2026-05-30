package com.example.pc01movilescardenas23100751palomino23200058

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.pc01movilescardenas23100751palomino23200058.ui.theme.PC01MOVILESCardenas23100751Palomino23200058Theme
import coil.compose.AsyncImage
import java.util.Locale

data class Destination(
    val country: String,
    val city: String,
    val averageCost: Double,
    val imageUrl: String,
    val description: String
)

val sampleDestinations = listOf(
    Destination("Egipto", "El Cairo", 950.0, "https://images.unsplash.com/photo-1572252009286-268acec5ca0a", "Hogar de las pirámides de Giza y la Esfinge, El Cairo es una ciudad llena de historia milenaria."),
    Destination("Francia", "París", 1500.0, "https://images.unsplash.com/photo-1502602898657-3e91760cbb34", "La Ciudad de la Luz, famosa por la Torre Eiffel, el Museo del Louvre y su ambiente romántico."),
    Destination("Italia", "Roma", 1200.0, "https://images.unsplash.com/photo-1552832230-c0197dd311b5", "La Ciudad Eterna, donde el Coliseo y el Vaticano cuentan historias del antiguo imperio."),
    Destination("Japón", "Tokio", 2000.0, "https://tipsparatuviaje.com/wp-content/uploads/2019/12/palacio-imperial.jpg", "Una metrópolis vibrante que combina rascacielos futuristas con templos tradicionales."),
    Destination("Perú", "Cusco", 800.0, "https://images.unsplash.com/photo-1526392060635-9d6019884377", "La capital del Imperio Inca, puerta de entrada a Machu Picchu y rica en cultura andina.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationScreen(navController: NavHostController) {
    var isDarkTheme by remember { mutableStateOf(false) }
    var selectedDestination by remember { mutableStateOf<Destination?>(null) }
    var showInstruction by remember { mutableStateOf(true) }
    
    val totalDestinations = sampleDestinations.size
    val totalCost = sampleDestinations.sumOf { it.averageCost }
    
    val globalTextColor = if (isDarkTheme) Color.LightGray else Color.Unspecified

    PC01MOVILESCardenas23100751Palomino23200058Theme(darkTheme = isDarkTheme) {
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                // Dialogo de imagen ampliada con descripción
                selectedDestination?.let { destination ->
                    Dialog(onDismissRequest = { selectedDestination = null }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column {
                                AsyncImage(
                                    model = destination.imageUrl,
                                    contentDescription = "Vista ampliada",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "${destination.city}, ${destination.country}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = destination.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Justify
                                    )
                                }
                            }
                        }
                    }
                }

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    "Catálogo de Destinos",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 24.sp,
                                    color = globalTextColor
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack, 
                                        contentDescription = "Regresar",
                                        tint = if (isDarkTheme) Color.LightGray else LocalContentColor.current
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                                    Icon(
                                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                        contentDescription = "Cambiar Modo",
                                        tint = if (isDarkTheme) Color.LightGray else LocalContentColor.current
                                    )
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
                            DestinationItem(
                                destination = destination,
                                textColor = globalTextColor,
                                onLongClick = { selectedDestination = destination }
                            )
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
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Cantidad total: $totalDestinations",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = globalTextColor
                                    )
                                    Text(
                                        text = "Suma acumulada de costos: $${String.format(Locale.US, "%.2f", totalCost)}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = globalTextColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Overlay de instrucción (mano blanca)
            if (showInstruction) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = { showInstruction = false })
                        },
                    color = Color.Black.copy(alpha = 0.7f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.BackHand,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Presiona prolongadamente un destino\npara activar la vista ampliada",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { showInstruction = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                        ) {
                            Text("Entendido")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DestinationItem(destination: Destination, textColor: Color, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown()
                    val longPress = withTimeoutOrNull(250) {
                        waitForUpOrCancellation()
                        false
                    } ?: true
                    
                    if (longPress) {
                        onLongClick()
                    }
                }
            },
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
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "${destination.city} - $${String.format(Locale.US, "%.2f", destination.averageCost)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
        }
    }
}
