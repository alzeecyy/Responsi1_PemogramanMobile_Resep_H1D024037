package com.example.responsi_pemmob_h1d024037

import com.example.responsi_pemmob_h1d024037.data.model.MealDto
import com.example.responsi_pemmob_h1d024037.data.model.extractIngredients
import com.example.responsi_pemmob_h1d024037.data.model.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MealDtoMappingTest {

    @Test
    fun extractIngredients_filtersEmptyAndBlankIngredients() {
        val dto = MealDto(
            idMeal = "123",
            strMeal = "Test Meal",
            strDrinkAlternate = null,
            strCategory = "Chicken",
            strArea = "Indonesian",
            strInstructions = "Cook it well",
            strMealThumb = "https://example.com/thumb.jpg",
            strTags = "Meat,Spicy",
            strYoutube = "https://youtube.com",
            strIngredient1 = "Chicken",
            strIngredient2 = "Garlic",
            strIngredient3 = "",
            strIngredient4 = "   ",
            strIngredient5 = null,
            strIngredient6 = null,
            strIngredient7 = null,
            strIngredient8 = null,
            strIngredient9 = null,
            strIngredient10 = null,
            strIngredient11 = null,
            strIngredient12 = null,
            strIngredient13 = null,
            strIngredient14 = null,
            strIngredient15 = null,
            strIngredient16 = null,
            strIngredient17 = null,
            strIngredient18 = null,
            strIngredient19 = null,
            strIngredient20 = null,
            strMeasure1 = "500g",
            strMeasure2 = "2 cloves",
            strMeasure3 = "",
            strMeasure4 = "",
            strMeasure5 = null,
            strMeasure6 = null,
            strMeasure7 = null,
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strMeasure16 = null,
            strMeasure17 = null,
            strMeasure18 = null,
            strMeasure19 = null,
            strMeasure20 = null
        )

        val ingredients = dto.extractIngredients()

        assertEquals(2, ingredients.size)
        assertEquals("Chicken", ingredients[0].name)
        assertEquals("500g", ingredients[0].measure)
        assertEquals("Garlic", ingredients[1].name)
        assertEquals("2 cloves", ingredients[1].measure)
    }

    @Test
    fun toDomain_convertsDtoSafelyWithNullValues() {
        val nullDto = MealDto(
            idMeal = null,
            strMeal = null,
            strDrinkAlternate = null,
            strCategory = null,
            strArea = null,
            strInstructions = null,
            strMealThumb = null,
            strTags = null,
            strYoutube = null,
            strIngredient1 = null,
            strIngredient2 = null,
            strIngredient3 = null,
            strIngredient4 = null,
            strIngredient5 = null,
            strIngredient6 = null,
            strIngredient7 = null,
            strIngredient8 = null,
            strIngredient9 = null,
            strIngredient10 = null,
            strIngredient11 = null,
            strIngredient12 = null,
            strIngredient13 = null,
            strIngredient14 = null,
            strIngredient15 = null,
            strIngredient16 = null,
            strIngredient17 = null,
            strIngredient18 = null,
            strIngredient19 = null,
            strIngredient20 = null,
            strMeasure1 = null,
            strMeasure2 = null,
            strMeasure3 = null,
            strMeasure4 = null,
            strMeasure5 = null,
            strMeasure6 = null,
            strMeasure7 = null,
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strMeasure16 = null,
            strMeasure17 = null,
            strMeasure18 = null,
            strMeasure19 = null,
            strMeasure20 = null
        )

        val domain = nullDto.toDomain()

        assertEquals("", domain.id)
        assertEquals("Resep Tanpa Nama", domain.name)
        assertEquals("Umum", domain.category)
        assertEquals("Internasional", domain.area)
        assertTrue(domain.tags.isEmpty())
        assertTrue(domain.ingredients.isEmpty())
    }
}
