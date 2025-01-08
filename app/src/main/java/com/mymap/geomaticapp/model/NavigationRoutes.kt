package com.mymap.geomaticapp.model

sealed class NavigationRoutes(val route: String) {

    data object Map: NavigationRoutes("Map")
    data object FormLocation: NavigationRoutes("FormLocation?lat={lat}?long={long}"){
        fun createRoute(lat: String, long: String) = "FormLocation?lat=$lat?long=$long"
    }

}