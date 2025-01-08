package com.mymap.geomaticapp.common

import androidx.compose.ui.graphics.Color
import com.mymap.geomaticapp.model.TypesLocation
import com.mymap.geomaticapp.ui.theme.Downy

object Utils {

    fun getIcon(type: Int): Int{
        return when(type){
            TypesLocation.SCHOOL.typeLocation -> TypesLocation.SCHOOL.icon
            TypesLocation.PARK.typeLocation -> TypesLocation.PARK.icon
            TypesLocation.CHURCH.typeLocation -> TypesLocation.CHURCH.icon
            TypesLocation.STORE.typeLocation -> TypesLocation.STORE.icon
            else -> TypesLocation.SCHOOL.icon
        }
    }

    fun getColor(type: Int): Color {
        return when(type){
            TypesLocation.SCHOOL.typeLocation -> TypesLocation.SCHOOL.color
            TypesLocation.PARK.typeLocation -> TypesLocation.PARK.color
            TypesLocation.CHURCH.typeLocation -> TypesLocation.CHURCH.color
            TypesLocation.STORE.typeLocation -> TypesLocation.STORE.color
            else -> Downy
        }
    }

    fun getName(type: Int): String {
        return when(type){
            TypesLocation.SCHOOL.typeLocation -> TypesLocation.SCHOOL.nameLocation
            TypesLocation.PARK.typeLocation -> TypesLocation.PARK.nameLocation
            TypesLocation.CHURCH.typeLocation -> TypesLocation.CHURCH.nameLocation
            TypesLocation.STORE.typeLocation -> TypesLocation.STORE.nameLocation
            else -> "Default"
        }
    }

}