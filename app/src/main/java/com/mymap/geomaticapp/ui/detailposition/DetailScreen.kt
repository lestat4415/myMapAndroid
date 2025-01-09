package com.mymap.geomaticapp.ui.detailposition

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mymap.geomaticapp.common.Utils
import com.mymap.geomaticapp.model.Positions
import com.mymap.geomaticapp.ui.theme.AllWhite

@Preview(showSystemUi = true)
@Composable
fun ShowPreviewInfoWindow() {
    MarkerInfoWindowCustom(position = Positions("", 0.0, 01.0, "22der5ttttraEsto es una prueba con una descripción muy larga", "Test name", "", 1))
}

@Composable
fun MarkerInfoWindowCustom(position: Positions) {
    Box(modifier = Modifier.padding(16.dp)){
        Box(
            modifier = Modifier.background(
                color = Utils.getColor(position.type),
                shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp)
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = Utils.getIcon(position.type)),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = "Nombre: ${position.name}",
                        color = AllWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Coordenadas: ${position.lat}, ${position.long}",
                        color = AllWhite,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Descripción: ${position.description}",
                        color = AllWhite,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Tipo: ${Utils.getName(position.type)}",
                        color = AllWhite,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

}
