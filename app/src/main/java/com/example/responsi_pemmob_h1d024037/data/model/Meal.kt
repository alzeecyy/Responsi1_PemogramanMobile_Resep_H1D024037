package com.example.responsi_pemmob_h1d024037.data.model

/**
 * Domain model untuk resep makanan yang bersih dan siap ditampilkan pada UI Composable.
 */
data class Meal(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumbnailUrl: String,
    val tags: List<String> = emptyList(),
    val youtubeUrl: String? = null,
    val ingredients: List<Ingredient> = emptyList()
)
