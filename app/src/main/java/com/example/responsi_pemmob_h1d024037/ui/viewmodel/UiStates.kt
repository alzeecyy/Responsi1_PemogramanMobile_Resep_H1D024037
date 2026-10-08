package com.example.responsi_pemmob_h1d024037.ui.viewmodel

import com.example.responsi_pemmob_h1d024037.data.model.Meal

/**
 * Sealed interface merepresentasikan state UI untuk Home Screen (State-Driven UI).
 */
sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val meals: List<Meal>) : HomeUiState
    data class Empty(val query: String) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

/**
 * Sealed interface merepresentasikan state UI untuk Recipe Detail Screen.
 */
sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val meal: Meal) : DetailUiState
    data class Error(val message: String) : DetailUiState
}
