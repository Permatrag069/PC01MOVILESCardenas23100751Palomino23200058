package com.example.pc01movilescardenas23100751palomino23200058

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pc01movilescardenas23100751palomino23200058.ui.theme.PC01MOVILESCardenas23100751Palomino23200058Theme

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
                        CalculadoraEquipajeScreen(navController)
                    }
                    composable("presupuesto") {
                        PantallaDestino("Planificador de Presupuesto", navController)
                    }
                    composable("catalogo") {
                        PantallaDestino("Catálogo de Destinos", navController)
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
fun CalculadoraEquipajeScreen(navController: NavHostController) {
    var pesoStr by remember { mutableStateOf("") }
    var tipoVuelo by remember { mutableStateOf("Nacional") }
    var resultado by remember { mutableStateOf("") }
    var errorPeso by remember { mutableStateOf<String?>(null) }

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Calculadora de Equipaje",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = pesoStr,
                onValueChange = {
                    pesoStr = it
                    errorPeso = null
                },
                label = { Text("Peso de la maleta (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorPeso != null,
                supportingText = {
                    if (errorPeso != null) {
                        Text(text = errorPeso!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Tipo de vuelo:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = tipoVuelo == "Nacional",
                        onClick = { tipoVuelo = "Nacional" }
                    )
                    Text("Nacional (máx 23 kg)")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = tipoVuelo == "Internacional",
                        onClick = { tipoVuelo = "Internacional" }
                    )
                    Text("Internacional (máx 32 kg)")
                }
            }

            Button(
                onClick = {
                    val peso = pesoStr.toDoubleOrNull()
                    if (pesoStr.isBlank()) {
                        errorPeso = "Campo obligatorio"
                        resultado = ""
                    } else if (peso == null) {
                        errorPeso = "Valor numérico requerido"
                        resultado = ""
                    } else if (peso <= 0) {
                        errorPeso = "Debe ser mayor a cero"
                        resultado = ""
                    } else {
                        errorPeso = null
                        val limite = if (tipoVuelo == "Nacional") 23.0 else 32.0
                        if (peso <= limite) {
                            resultado = "Si cumple el límite permitido"
                        } else {
                            val excedido = peso - limite
                            resultado = "Si excede el límite permitido\nCantidad de kg excedidos: ${"%.2f".format(excedido)} kg"
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Calcular", style = MaterialTheme.typography.titleMedium)
            }

            if (resultado.isNotEmpty()) {
                Text(
                    text = resultado,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (resultado.contains("excede")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
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
