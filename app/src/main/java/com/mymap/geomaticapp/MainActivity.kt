package com.mymap.geomaticapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.mymap.geomaticapp.ui.formlocation.FormLocationViewModel
import com.mymap.geomaticapp.ui.map.MapViewModel
import com.mymap.geomaticapp.ui.theme.GeomaticAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    private val mapViewModel: MapViewModel by viewModels()

    private val formViewModel: FormLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            navHostController = rememberNavController()

            GeomaticAppTheme {
                MapApp(
                    navHostController = navHostController,
                    mapViewModel = mapViewModel,
                    formViewModel = formViewModel
                )
            }
        }
        obtenerUbicacionActual()
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacionActual() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                lifecycleScope.launch {
                    mapViewModel.actualizarUbicacionActual(currentLatLng)
                    mapViewModel.updateLocationClicked(currentLatLng)
                }
            }
        }
    }
}

@Composable
fun MapApp(
    navHostController: NavHostController,
    mapViewModel: MapViewModel,
    formViewModel: FormLocationViewModel
) {
    var permisoConcedido by remember { mutableStateOf(false) }
    val solicitarPermisos = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permisoConcedido = isGranted
    }

    LaunchedEffect(Unit) {
        solicitarPermisos.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    if (permisoConcedido) {
        NavigationWrapper(
            navHostController = navHostController,
            mapViewModel = mapViewModel,
            formViewModel = formViewModel
        )
    }
}