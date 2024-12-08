package ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto


data class RecipeDetailDTO(
    val recipeID: Long? = 0,
    val name: String? = "No name",
    val description: String? = "No description",
    val thumbnailUrl: String? = "",
    val keywords: String? = "",
    val isPublic: Boolean? = false,
    val userEmail: String? = "",
    val originalVideoUrl: String? = "",
    val country: String? = "",
    val numServings: Long?=0,
    val components: List<ComponentDTO?>? = emptyList(),
    val instructions: List<InstructionDTO?>?= emptyList(),
    val nutrition: NutritionDTO?,
)












