package ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto


data class RecipeDetailDTO(
    val recipeId: Long,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val keywords: String,
    val isPublic: Boolean,
    val userEmail: String,
    val originalVideoUrl: String,
    val country: String,
    val numServings: Long,
    val components: List<ComponentDTO>,
    val instructions: List<InstructionDTO>,
    val nutrition: NutritionDTO,
)












