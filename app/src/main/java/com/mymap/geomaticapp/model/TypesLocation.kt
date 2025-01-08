package com.mymap.geomaticapp.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.mymap.geomaticapp.R
import com.mymap.geomaticapp.ui.theme.Blue
import com.mymap.geomaticapp.ui.theme.Brown
import com.mymap.geomaticapp.ui.theme.Green
import com.mymap.geomaticapp.ui.theme.Yellow

enum class TypesLocation(
    val typeLocation: Int,
    val nameLocation: String,
    @DrawableRes val icon: Int,
    val color: Color
) {
    SCHOOL(typeLocation = 1, nameLocation = "Escuela", icon = R.drawable.ic_school, color = Blue),
    PARK(typeLocation = 2, nameLocation = "Parque", icon = R.drawable.ic_park, color = Green),
    CHURCH(typeLocation = 3, nameLocation = "Iglesia", icon = R.drawable.ic_church, color = Brown),
    STORE(typeLocation = 4, nameLocation = "Comercio", icon = R.drawable.ic_store, color = Yellow);

    companion object {
        fun getListOptions(): List<TypesLocation> =
            listOf(
                SCHOOL,
                PARK,
                CHURCH,
                STORE,
            )
    }

}