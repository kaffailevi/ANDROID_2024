package ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto

data class ComponentDTO(
    val rawText: String,
    val extraComment: String,
    val ingredient: IngredientDTO,
    val measurement: MeasurementDTO,
    val position: Long,
)