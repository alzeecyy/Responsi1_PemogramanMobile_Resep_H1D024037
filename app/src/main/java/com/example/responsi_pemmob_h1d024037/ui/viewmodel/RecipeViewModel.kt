package com.example.responsi_pemmob_h1d024037.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.responsi_pemmob_h1d024037.data.model.Meal
import com.example.responsi_pemmob_h1d024037.data.repository.MealRepository
import com.example.responsi_pemmob_h1d024037.data.repository.MealRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel untuk mengelola UI State dan berinteraksi dengan MealRepository (Pola MVVM).
 */
class RecipeViewModel(
    private val repository: MealRepository = MealRepositoryImpl()
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private var allLoadedMeals: List<Meal> = emptyList()
    private var searchJob: Job? = null

    init {
        // Muat resep awal saat aplikasi dibuka
        fetchRecipes("")
    }

    /**
     * Mengubah query pencarian dan melakukan debounce otomatis untuk mencegah spam request API.
     */
    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400) // Debounce 400ms
            if (_selectedCategory.value != "All") {
                _selectedCategory.value = "All"
            }
            fetchRecipes(newQuery)
        }
    }

    /**
     * Menjalankan pencarian manual (misal saat menekan tombol search keyboard).
     */
    fun onSearchSubmitted() {
        searchJob?.cancel()
        fetchRecipes(_searchQuery.value)
    }

    /**
     * Memilih filter kategori cepat (All, Chicken, Beef, Seafood, Dessert, Vegetarian, Pasta).
     */
    fun onCategorySelected(category: String) {
        if (_selectedCategory.value == category) return
        _selectedCategory.value = category
        searchJob?.cancel()

        if (category == "All") {
            _searchQuery.value = ""
            fetchRecipes("")
        } else {
            _searchQuery.value = category
            fetchRecipes(category)
        }
    }

    /**
     * Mengambil daftar resep dari repository berdasarkan keyword.
     */
    fun fetchRecipes(query: String) {
        viewModelScope.launch {
            _homeUiState.value = HomeUiState.Loading
            repository.searchMeals(query)
                .onSuccess { meals ->
                    allLoadedMeals = meals
                    _homeUiState.value = if (meals.isEmpty()) {
                        HomeUiState.Empty(query = query)
                    } else {
                        HomeUiState.Success(meals = meals)
                    }
                }
                .onFailure { throwable ->
                    val errorMessage = throwable.localizedMessage ?: "Terjadi kesalahan saat mengambil data resep. Periksa koneksi internet Anda."
                    _homeUiState.value = HomeUiState.Error(message = errorMessage)
                }
        }
    }

    /**
     * Mengambil data detail resep berdasarkan ID makanan.
     */
    fun fetchRecipeDetail(mealId: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailUiState.Loading
            repository.getMealDetail(mealId)
                .onSuccess { meal ->
                    _detailUiState.value = DetailUiState.Success(meal = meal)
                }
                .onFailure { throwable ->
                    val errorMessage = throwable.localizedMessage ?: "Gagal memuat detail resep. Silakan coba lagi."
                    _detailUiState.value = DetailUiState.Error(message = errorMessage)
                }
        }
    }

    /**
     * Fungsi retry untuk Home Screen jika terjadi error koneksi.
     */
    fun retryHome() {
        fetchRecipes(_searchQuery.value)
    }

    /**
     * Fungsi retry untuk Detail Screen jika terjadi error koneksi.
     */
    fun retryDetail(mealId: String) {
        fetchRecipeDetail(mealId)
    }
}
