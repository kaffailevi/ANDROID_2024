package ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto

data class NutritionDTO(
    val calories: Long,
    val protein: Long,
    val fat: Long,
    val carbohydrates: Long,
    val sugar: Long,
    val fiber: Long,
)