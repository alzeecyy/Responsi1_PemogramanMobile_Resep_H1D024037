package com.example.responsi_pemmob_h1d024037.data.remote

import com.example.responsi_pemmob_h1d024037.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface Retrofit untuk endpoint TheMealDB API.
 */
interface MealApiService {

    /**
     * Mencari resep makanan berdasarkan nama atau query.
     * Endpoint: https://www.themealdb.com/api/json/v1/1/search.php?s={nama_makanan}
     */
    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealResponse

    /**
     * Mengambil detail resep makanan berdasarkan ID resep.
     * Endpoint: https://www.themealdb.com/api/json/v1/1/lookup.php?i={id_recipe}
     */
    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") id: String
    ): MealResponse
}
