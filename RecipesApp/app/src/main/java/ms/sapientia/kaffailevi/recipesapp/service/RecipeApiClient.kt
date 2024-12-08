package ms.sapientia.kaffailevi.recipesapp.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDetailDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RecipeApiClient {

    private val recipeService: RecipeService

    init {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }

        val okHttpClient: OkHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(TOKEN))
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        recipeService = retrofit.create(RecipeService::class.java)
    }


    suspend fun getRecipes(): List<RecipeDTO>? {
        return withContext(Dispatchers.IO) {
            try {
                recipeService.getRecipes()
            } catch (e: Exception) {
// Handle exceptions here
                null
            }
        }
    }

    suspend fun getRecipeDetail(id: Long): RecipeDetailDTO? {
        return withContext(Dispatchers.IO) {
            try {
                recipeService.getRecipeById(id)
            } catch (e: Exception) {
// Handle exceptions here
                null
            }
        }
    }

    suspend fun getMyRecipes(): List<RecipeDTO>? {
        return withContext(Dispatchers.IO) {
            try {
                recipeService.getMyRecipes()
            } catch (e: Exception) {
// Handle exceptions here
                null
            }
        }
    }

    suspend fun createRecipe(recipe: RecipeDetailDTO): RecipeDetailDTO? {
        return withContext(Dispatchers.IO) {
            try {
                recipeService.createRecipe(recipe)
            } catch (e: Exception) {
                Log.e("RecipeAPiClient", e.toString())
                null
            }
        }
    }

    suspend fun deleteRecipe(recipeId: Long) {
        return withContext(Dispatchers.IO) {
            try {
                recipeService.deleteRecipe(recipeId)
            } catch (e: Exception) {
// Handle exceptions here
            }
        }
    }


}