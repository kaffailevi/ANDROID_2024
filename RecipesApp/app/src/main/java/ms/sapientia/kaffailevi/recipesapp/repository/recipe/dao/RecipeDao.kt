package ms.sapientia.kaffailevi.recipesapp.repository.recipe.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dagger.hilt.android.AndroidEntryPoint
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.entity.RecipeEntity
import javax.inject.Singleton

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity)
    @Query("SELECT * FROM recipe WHERE internalId = :id")
    suspend fun getRecipeById(id: Long): RecipeEntity?
    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes(): List<RecipeEntity>
    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
    @Query("SELECT EXISTS (SELECT 1 FROM recipe WHERE internalId = :recipeId)")
    suspend fun isRecipeSaved(recipeId: Long): Boolean
}