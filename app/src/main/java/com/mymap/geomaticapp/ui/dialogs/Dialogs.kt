package com.mymap.geomaticapp.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mymap.geomaticapp.R
import com.mymap.geomaticapp.ui.formlocation.MyButton

@Composable
fun PositionCreatedDialog(show: Boolean, onDismiss: () -> Unit) {
    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 12.dp),
                    painter = painterResource(id = R.drawable.ic_position_created),
                    contentDescription = "",
                    alpha = 0.5f
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    text = "¡La posición ha sido creada!",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Ahora puedes ver la posición en el mapa",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(vertical = 12.dp))
                MyButton(text = "Ver en el mapa", isEnabled = true) {
                    onDismiss()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewDialogs() {
    PositionCreatedDialog(true) {

    }
}