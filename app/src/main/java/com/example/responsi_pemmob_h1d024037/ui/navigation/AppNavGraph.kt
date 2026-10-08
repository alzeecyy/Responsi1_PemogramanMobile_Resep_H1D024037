package com.example.responsi_pemmob_h1d024037.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.responsi_pemmob_h1d024037.ui.screens.DetailScreen
import com.example.responsi_pemmob_h1d024037.ui.screens.HomeScreen
import com.example.responsi_pemmob_h1d024037.ui.viewmodel.RecipeViewModel

/**
 * AppNavGraph: Mengatur grafik navigasi antara Home Screen dan Detail Screen dengan NavHost Jetpack Compose.
 */
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: RecipeViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onRecipeClick = { mealId ->
                    navController.navigate(Screen.Detail.createRoute(mealId))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("mealId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId").orEmpty()
            DetailScreen(
                mealId = mealId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
