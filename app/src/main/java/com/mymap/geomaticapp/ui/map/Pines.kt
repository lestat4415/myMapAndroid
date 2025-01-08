package com.mymap.geomaticapp.ui.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.mymap.geomaticapp.common.Utils
import com.mymap.geomaticapp.model.Positions

@Composable
fun PositionRegistered(
    position: Positions,
    onClick: () -> Unit
) {
    val markerState = remember { MarkerState(position = LatLng(position.lat, position.long)) }
    val shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 0.dp)

    MarkerComposable(
        keys = arrayOf(position.name),
        state = markerState,
        title = position.name,
        anchor = Offset(0.5f, 1f)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(shape)
                .background(Utils.getColor(position.type))
                .padding(4.dp)
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = Utils.getIcon(position.type)),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop
            )
        }
    }
}