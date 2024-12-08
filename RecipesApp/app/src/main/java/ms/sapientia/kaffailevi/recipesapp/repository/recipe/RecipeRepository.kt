package ms.sapientia.kaffailevi.recipesapp.repository.recipe

import android.content.Context
import com.google.gson.Gson
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dao.RecipeDao
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.entity.RecipeEntity
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeDetailModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import ms.sapientia.kaffailevi.recipesapp.service.RecipeApiClient
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeApiClient: RecipeApiClient
) {

    private val gson = Gson()

    suspend fun insertRecipeToLocalDb(recipe: RecipeModel) {
        val recipeJson = gson.toJson(recipe)
        val recipeEntity = RecipeEntity(json = recipeJson, internalId = recipe.recipeId)
        recipeDao.insertRecipe(recipeEntity)
    }

    suspend fun getMyRecipes(): List<RecipeModel> {
        return recipeApiClient.getMyRecipes()?.toRecipeModelList() ?: emptyList()
    }

    suspend fun createRecipe(recipe: RecipeDetailModel): RecipeDetailModel? {
        return recipeApiClient.createRecipe(recipe.toDTO())?.toModel()
    }


    suspend fun getAllFromAPI(): List<RecipeModel> {
//        readAll(context)
//        return recipeList
        return recipeApiClient.getRecipes()?.toRecipeModelList() ?: emptyList()
    }

    suspend fun getRecipeDetail(recipeId: Long): RecipeDetailModel? {
//        readAll(context)
//        return recipeDetailModel
        return recipeApiClient.getRecipeDetail(recipeId)?.toModel()

    }


    suspend fun getSavedRecipesFromLocalDB(): List<RecipeModel> {
        return recipeDao.getAllRecipes().map {
            val jsonObject = JSONObject(it.json)

            val model = gson.fromJson(jsonObject.toString(), RecipeDTO::class.java).toModel()
            model.recipeId = it.internalId
            model
        }
    }

    suspend fun isRecipeSavedLocally(recipeModel: RecipeModel): Boolean {

        return recipeDao.isRecipeSaved(recipeModel.recipeId)
    }

    suspend fun deleteRecipeFromLocalDb(recipeId: Long) {
        recipeDao.getRecipeById(recipeId)?.let {
            this.recipeDao.deleteRecipe(it)
        }
    }

    suspend fun deleteRecipeFromApi(recipeId: Long) {
        recipeApiClient.deleteRecipe(recipeId)
    }


}


