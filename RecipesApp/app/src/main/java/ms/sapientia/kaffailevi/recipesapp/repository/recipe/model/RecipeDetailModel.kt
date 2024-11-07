package ms.sapientia.kaffailevi.recipesapp.repository.recipe.model


data class RecipeDetailModel(
//    val recipeId: Long,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val keywords: String,
    val isPublic: Boolean,
    val userEmail: String,
    val originalVideoUrl: String,
    val country: String,
    val numServings: Long,
    val components: List<ComponentModel>,
    val instructions: List<InstructionModel>,
    val nutrition: NutritionModel,
)












