package com.mymap.geomaticapp.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.mymap.geomaticapp.R
import com.mymap.geomaticapp.model.Positions
import com.mymap.geomaticapp.ui.detailposition.MarkerInfoWindowCustom
import com.mymap.geomaticapp.ui.theme.Astral
import com.mymap.geomaticapp.ui.theme.Blue
import com.mymap.geomaticapp.ui.theme.White

@Composable
fun MapScreen(
    navigateToFormLocationScreen: (lat: String, lng: String) -> Unit,
    mapViewModel: MapViewModel
) {

    LaunchedEffect(Unit) {
        mapViewModel.initViewModel()
    }

    Scaffold(
        floatingActionButton = { MyFAB(navigateToFormLocationScreen, mapViewModel) },
        floatingActionButtonPosition = FabPosition.Start
    ) {
        MapaGoogle(viewModel = mapViewModel, paddingValues = it)
    }

}

@Composable
fun MapaGoogle(viewModel: MapViewModel, paddingValues: PaddingValues) {

    val ubicacionActual by viewModel.ubicacionActual.collectAsState()
    val locationClicked by viewModel.ubicationClicked.collectAsState()

    val currentPositions: List<Positions> by viewModel.positions.observeAsState(
        initial = emptyList()
    )

    val cameraPositionState = rememberCameraPositionState()

    var selectedMarker by remember { mutableStateOf<Positions?>(null) }

    // Mover la cámara a la ubicación actual
    LaunchedEffect(ubicacionActual) {
        Log.d("MapScreen", "ubicacionActual: $ubicacionActual")
        ubicacionActual.let {
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(it ?: viewModel.defaultLocation, 15f)
        }
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            viewModel.updateLocationClicked(it)
        }
    ) {

        UserCurrenPositionMarker(
            fullName = "You",
            location = ubicacionActual ?: LatLng(19.42847, -99.12766)
        ) {

        }

        locationClicked?.let {
            Marker(
                state = MarkerState(position = it), // Ejemplo de posición
                icon = pointNewPosition(Astral),
                title = "Nueva Posición",
                snippet = ""
            )
        }

        currentPositions.forEach { position ->
            MarkerInfoWindow(
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
                state = MarkerState(position = LatLng(position.lat, position.long))
            ) {
                MarkerInfoWindowCustom(position)
            }
        }
    }
}

@Composable
fun UserCurrenPositionMarker(
    fullName: String,
    location: LatLng,
    onClick: () -> Unit
) {
    val markerState = remember { MarkerState(position = location) }
    val shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 0.dp)

    MarkerComposable(
        keys = arrayOf(fullName),
        state = markerState,
        title = fullName,
        anchor = Offset(0.5f, 1f),
        onClick = {
            onClick()
            true
        }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(shape)
                .background(Blue)
                .padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun pointNewPosition(myColor: Color): BitmapDescriptor {
    // Crear un Bitmap desde un Canvas
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Crear el pin
    val paint = Paint().apply {
        color = myColor.toArgb()
        isAntiAlias = true
    }

    // Dibujar un círculo (el pin)
    canvas.drawCircle(50f, 50f, 40f, paint)

    // Crear el descriptor del pin
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


@Composable
fun MyFAB(
    navigateToFormLocationScreen: (lat: String, lng: String) -> Unit,
    mapViewModel: MapViewModel
) {

    val mContext = LocalContext.current

    FloatingActionButton(
        onClick = {
            if (mapViewModel.ubicationClicked.value != null) {
                navigateToFormLocationScreen(
                    mapViewModel.ubicationClicked.value?.latitude.toString(),
                    mapViewModel.ubicationClicked.value?.longitude.toString()
                )
            } else {
                mToast(context = mContext, "Selecciona una ubicación en el mapa")
            }
        },
        containerColor = Astral,
        contentColor = White
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

private fun mToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@Preview(showSystemUi = true)
@Composable
fun showPreviewMap() {
}

