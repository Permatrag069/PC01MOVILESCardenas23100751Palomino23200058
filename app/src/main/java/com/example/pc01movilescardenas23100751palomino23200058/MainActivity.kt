package com.example.pc01movilescardenas23100751palomino23200058

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pc01movilescardenas23100751palomino23200058.ui.theme.PC01MOVILESCardenas23100751Palomino23200058Theme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC01MOVILESCardenas23100751Palomino23200058Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "menu") {
                    composable("menu") {
                        MenuPrincipalScreen(navController)
                    }
                    composable("calculadora") {
                        PantallaDestino("Calculadora de Equipaje", navController)
                    }
                    composable("presupuesto") {
                        PresupuestoScreen(navController)
                    }
                    composable("catalogo") {
                        DestinationScreen(navController)
                    }
                    composable("permiso") {
                        PantallaDestino("Permiso de Ubicación", navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Asistente de Viaje",
                        style = MaterialTheme.typography.headlineMedium
                    ) 
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("calculadora") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Text("Calculadora de Equipaje", style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("presupuesto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Text("Planificador de Presupuesto", style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("catalogo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Text("Catálogo de Destinos", style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("permiso") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Text("Permiso de Ubicación", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresupuestoScreen(navController: NavHostController) {
    var dias by remember { mutableStateOf("") }
    var presupuestoDiario by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var tipoSeleccionado by remember { mutableStateOf("Estándar") }
    var factorSeleccionado by remember { mutableFloatStateOf(1.0f) }
    
    val opciones = listOf(
        "Económico" to 0.8f,
        "Estándar" to 1.0f,
        "Premium" to 1.5f
    )

    var totalCalculado by remember { mutableStateOf<Double?>(null) }
    var mensajeResultado by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PLANIFICADOR DE PRESUPUESTO") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = dias,
                onValueChange = { if (it.all { char -> char.isDigit() }) dias = it },
                label = { Text("Cantidad de días") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = presupuestoDiario,
                onValueChange = { if (it.isEmpty() || it.toDoubleOrNull() != null || it == ".") presupuestoDiario = it },
                label = { Text("Presupuesto diario ($)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = tipoSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de alojamiento") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opciones.forEach { (nombre, factor) ->
                        DropdownMenuItem(
                            text = { Text(nombre) },
                            onClick = {
                                tipoSeleccionado = nombre
                                factorSeleccionado = factor
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val d = dias.toIntOrNull()
                    val p = presupuestoDiario.toDoubleOrNull()
                    
                    if (d == null || p == null) {
                        errorMensaje = "Todos los campos son obligatorios."
                        totalCalculado = null
                    } else if (d <= 0) {
                        errorMensaje = "La cantidad de días debe ser mayor a cero."
                        totalCalculado = null
                    } else if (p <= 0) {
                        errorMensaje = "El presupuesto diario debe ser mayor a cero."
                        totalCalculado = null
                    } else {
                        errorMensaje = ""
                        val total = d * p * factorSeleccionado
                        totalCalculado = total
                        mensajeResultado = when (tipoSeleccionado) {
                            "Económico" -> "Escenario ahorro: Ideal para viajeros con presupuesto limitado."
                            "Estándar" -> "Escenario balanceado: Comodidad y precio justo."
                            "Premium" -> "Escenario de lujo: Máximo confort para tu viaje."
                            else -> ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Calcular Presupuesto Total", style = MaterialTheme.typography.titleMedium)
            }

            if (errorMensaje.isNotEmpty()) {
                Text(
                    text = errorMensaje,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            totalCalculado?.let { total ->
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Presupuesto Total: $" + String.format(Locale.US, "%.2f", total),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = mensajeResultado,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Regresar al Menú")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDestino(titulo: String, navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        titulo,
                        style = MaterialTheme.typography.headlineSmall
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a $titulo", 
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(64.dp)
            ) {
                Text("Regresar al Menú", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}
