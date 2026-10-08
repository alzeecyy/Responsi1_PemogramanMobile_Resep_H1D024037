package com.example.responsi_pemmob_h1d024037.ui.navigation

/**
 * Sealed class mendefinisikan rute navigasi antar layar dalam aplikasi.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{mealId}") {
        fun createRoute(mealId: String): String = "detail/$mealId"
    }
}
