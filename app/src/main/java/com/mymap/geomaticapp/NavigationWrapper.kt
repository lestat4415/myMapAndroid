package com.mymap.geomaticapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mymap.geomaticapp.model.NavigationRoutes
import com.mymap.geomaticapp.ui.formlocation.FormLocationScreen
import com.mymap.geomaticapp.ui.formlocation.FormLocationViewModel
import com.mymap.geomaticapp.ui.map.MapScreen
import com.mymap.geomaticapp.ui.map.MapViewModel

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    mapViewModel: MapViewModel,
    formViewModel: FormLocationViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = NavigationRoutes.Map.route
    ) {
        composable(NavigationRoutes.Map.route) {
            MapScreen(
                navigateToFormLocationScreen = { lat, long ->
                    navHostController.navigate(NavigationRoutes.FormLocation.createRoute(
                        lat = lat,
                        long = long
                    ))
                },
                mapViewModel = mapViewModel
            )
        }
        composable(NavigationRoutes.FormLocation.route,
            arguments = listOf(navArgument("lat") {
                defaultValue = ""
                type = NavType.StringType
            },
                navArgument("long") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            FormLocationScreen(
                lat = backStackEntry.arguments?.getString("lat"),
                lon = backStackEntry.arguments?.getString("long"),
                viewModel = formViewModel,
                backPressed = {
                    navHostController.popBackStack()
                }
            )
        }
    }

}