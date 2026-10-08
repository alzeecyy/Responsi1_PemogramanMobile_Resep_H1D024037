package com.example.responsi_pemmob_h1d024037.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

val PopularCategories = listOf(
    "All" to "Semua",
    "Chicken" to "Ayam",
    "Beef" to "Daging Sapi",
    "Seafood" to "Seafood",
    "Dessert" to "Dessert",
    "Pasta" to "Pasta",
    "Vegetarian" to "Vegetarian",
    "Breakfast" to "Sarapan",
    "Side" to "Makanan Pendamping"
)

/**
 * Reusable Composable baris chip kategori masakan yang dapat di-scroll horizontal.
 */
@Composable
fun CategoryChipGroup(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PopularCategories.forEach { (categoryKey, categoryLabel) ->
            val isSelected = selectedCategory.equals(categoryKey, ignoreCase = true)

            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(categoryKey) },
                label = {
                    Text(
                        text = categoryLabel,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                border = null
            )
        }
    }
}
