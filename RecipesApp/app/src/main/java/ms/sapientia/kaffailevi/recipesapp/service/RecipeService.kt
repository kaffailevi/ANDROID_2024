package ms.sapientia.kaffailevi.recipesapp.service

import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDetailDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @GET("api/recipes")
    suspend fun getRecipes(): List<RecipeDTO>
    @GET("api/recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: Long): RecipeDetailDTO
    @GET("/api/recipes/my")
    suspend fun getMyRecipes(): List<RecipeDTO>?
    @POST("/api/recipes")
    suspend fun createRecipe(@Body recipe: RecipeDetailDTO): RecipeDetailDTO
    @DELETE("/api/recipes/{id}")
    suspend fun deleteRecipe(@Path("id") id: Long)

}
