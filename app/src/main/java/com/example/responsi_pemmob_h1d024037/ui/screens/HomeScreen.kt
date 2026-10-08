package com.example.responsi_pemmob_h1d024037.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.responsi_pemmob_h1d024037.ui.components.CategoryChipGroup
import com.example.responsi_pemmob_h1d024037.ui.components.EmptyStateView
import com.example.responsi_pemmob_h1d024037.ui.components.ErrorStateView
import com.example.responsi_pemmob_h1d024037.ui.components.LoadingStateView
import com.example.responsi_pemmob_h1d024037.ui.components.RecipeCard
import com.example.responsi_pemmob_h1d024037.ui.components.SearchBarView
import com.example.responsi_pemmob_h1d024037.ui.viewmodel.HomeUiState
import com.example.responsi_pemmob_h1d024037.ui.viewmodel.RecipeViewModel

/**
 * Home Screen: Menampilkan judul aplikasi, search bar, filter kategori,
 * dan katalog resep menggunakan LazyVerticalGrid dengan state-driven UI yang sederhana dan bersih.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: RecipeViewModel,
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.homeUiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Katalog Resep",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Kolom Pencarian
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                SearchBarView(
                    query = searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChanged(it) },
                    onSearchSubmit = { viewModel.onSearchSubmitted() }
                )
            }

            // Filter Kategori Cepat
            CategoryChipGroup(
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.onCategorySelected(it) }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Pengelolaan State UI
            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    LoadingStateView(
                        message = "Memuat resep...",
                        modifier = Modifier.weight(1f)
                    )
                }

                is HomeUiState.Error -> {
                    ErrorStateView(
                        message = state.message,
                        onRetry = { viewModel.retryHome() },
                        modifier = Modifier.weight(1f)
                    )
                }

                is HomeUiState.Empty -> {
                    EmptyStateView(
                        query = state.query,
                        modifier = Modifier.weight(1f)
                    )
                }

                is HomeUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 4.dp,
                            bottom = 20.dp
                        ),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        items(
                            items = state.meals,
                            key = { meal -> meal.id }
                        ) { meal ->
                            RecipeCard(
                                meal = meal,
                                onClick = onRecipeClick
                            )
                        }
                    }
                }
            }
        }
    }
}
