package com.example.responsi_pemmob_h1d024037.data.repository

import com.example.responsi_pemmob_h1d024037.data.model.Meal
import com.example.responsi_pemmob_h1d024037.data.model.toDomain
import com.example.responsi_pemmob_h1d024037.data.remote.MealApiService
import com.example.responsi_pemmob_h1d024037.data.remote.RetrofitInstance

/**
 * Interface Repository untuk mengabstraksi sumber data resep makanan.
 */
interface MealRepository {
    suspend fun searchMeals(query: String): Result<List<Meal>>
    suspend fun getMealDetail(id: String): Result<Meal>
}

/**
 * Implementasi konkret MealRepository yang berkomunikasi dengan TheMealDB API melalui Retrofit.
 */
class MealRepositoryImpl(
    private val apiService: MealApiService = RetrofitInstance.apiService
) : MealRepository {

    override suspend fun searchMeals(query: String): Result<List<Meal>> {
        return runCatching {
            val response = apiService.searchMeals(query)
            // Memanfaatkan lambda, null-safety, dan extension function toDomain()
            response.meals?.map { it.toDomain() } ?: emptyList()
        }
    }

    override suspend fun getMealDetail(id: String): Result<Meal> {
        return runCatching {
            val response = apiService.getMealDetail(id)
            val mealDto = response.meals?.firstOrNull()
                ?: throw NoSuchElementException("Resep dengan ID $id tidak ditemukan.")
            mealDto.toDomain()
        }
    }
}
