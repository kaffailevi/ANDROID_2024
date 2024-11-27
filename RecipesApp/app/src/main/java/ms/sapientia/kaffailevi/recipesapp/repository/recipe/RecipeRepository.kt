package ms.sapientia.kaffailevi.recipesapp.repository.recipe

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dao.RecipeDao
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.ComponentDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.IngredientDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.InstructionDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.MeasurementDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.NutritionDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDetailDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.UnitDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.entity.RecipeEntity
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.ComponentModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.IngredientModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.InstructionModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.MeasurementModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.NutritionModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeDetailModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.UnitModel
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RecipeRepository @Inject constructor(private val recipeDao: RecipeDao) {
    private lateinit var recipeList: MutableList<RecipeModel>
    private lateinit var recipeDetailModel: RecipeDetailModel
    private val gson = Gson()

    suspend fun insertRecipeToLocalDb(recipe: RecipeModel) {
        val recipeJson = gson.toJson(recipe)
        val recipeEntity = RecipeEntity(json = recipeJson)
        recipeDao.insertRecipe(recipeEntity)
    }



    suspend fun getMyRecipes(): List<RecipeModel> {
        return recipeDao.getAllRecipes().map {
            val jsonObject = JSONObject(it.json)
            jsonObject.apply { put("id", it.internalId) }
            gson.fromJson(jsonObject.toString(), RecipeDTO::class.java).toModel()
        }
    }


    fun getAll(context: Context): MutableList<RecipeModel> {
        readAll(context)
        return recipeList
    }
    fun getDetail(context: Context): RecipeDetailModel {
        readAll(context)
        return recipeDetailModel
    }

    private fun readAll(context: Context){
        val recipesJsonString = readJsonFromAssets(context, "more_recipes.json")
        val recipesDetailJsonString = readJsonFromAssets(context, "one_recipe_example.json")
        val recipeListListDTO: List<RecipeDTO>? =
            recipesJsonString?.let { parseJson<List<RecipeDTO>>(it) }
        val recipeDetailDTO: RecipeDetailDTO? =
            recipesDetailJsonString?.let { parseJson<RecipeDetailDTO>(it) }

        this.recipeList = recipeListListDTO?.toRecipeModelList() as MutableList<RecipeModel>
        this.recipeDetailModel = recipeDetailDTO?.toModel() as RecipeDetailModel
    }



    private fun readJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            Log.e("Error while JSON Parsing", "Error while file handling: ${ex.message}")
            return null
        }
    }

    private inline fun <reified T> parseJson(jsonString: String): T? {
        return try {
            val type = object : TypeToken<T>() {}.type // This specifies the correct type at runtime
            gson.fromJson<T>(jsonString, type)
        } catch (e: JsonSyntaxException) {
            Log.e("JSON Parsing Error", "Error parsing JSON: ${e.message}")
            null
        }
    }
    private fun RecipeDTO.toModel(): RecipeModel {
        return RecipeModel(
            name = this.name,
            description = this.description,
            thumbnailUrl = this.thumbnailUrl,
            keywords = this.keywords,
            isPublic = this.isPublic,
            userEmail = this.userEmail,
            originalVideoUrl = this.originalVideoUrl,
            country = this.country,
            numServings = this.numServings,
            components = this.components.toComponentModelList(),
            instructions = this.instructions.toInstructionModelList(),
        )
    }



    private fun RecipeDetailDTO.toModel(): RecipeDetailModel {
        return RecipeDetailModel(
            name = this.name,
            description = this.description,
            thumbnailUrl = this.thumbnailUrl,
            keywords = this.keywords,
            isPublic = this.isPublic,
            userEmail = this.userEmail,
            originalVideoUrl = this.originalVideoUrl,
            country = this.country,
            numServings = this.numServings,
            components = this.components.toComponentModelList(),
            instructions = this.instructions.toInstructionModelList(),
            nutrition = this.nutrition.toModel(),
        )
    }

    private fun NutritionDTO.toModel(): NutritionModel {
        return NutritionModel(
            calories = this.calories,
            protein = this.protein,
            fat = this.fat,
            carbohydrates = this.carbohydrates,
            sugar = this.sugar,
            fiber = this.fiber
        )
    }



    private fun MeasurementDTO.toModel(): MeasurementModel {
        return MeasurementModel(
            quantity = this.quantity, unit = this.unit.toModel()
        )
    }



    private fun InstructionDTO.toModel(): InstructionModel {
        return InstructionModel(
            position = this.position, displayText = this.displayText
        )
    }

    private fun IngredientDTO.toModel(): IngredientModel {
        return IngredientModel(
            name = this.name
        )
    }

    private fun ComponentDTO.toModel(): ComponentModel {
        return ComponentModel(
            rawText = this.rawText,
            extraComment = this.extraComment,
            ingredient = this.ingredient.toModel(),
            measurement = this.measurement.toModel(),
            position = this.position
        )
    }

    fun List<ComponentDTO>.toComponentModelList(): List<ComponentModel> {
        return this.map { it.toModel() }
    }

    fun List<InstructionDTO>.toInstructionModelList(): List<InstructionModel> {
        return this.map { it.toModel() }
    }

    fun List<MeasurementDTO>.toMeasurementModelList(): List<MeasurementModel> {
        return this.map { it.toModel() }
    }

    fun List<NutritionDTO>.toNutritionModelList(): List<NutritionModel> {
        return this.map { it.toModel() }
    }

    fun List<RecipeDTO>.toRecipeModelList(): List<RecipeModel> {
        return this.map { it.toModel() }
    }
    private fun UnitDTO.toModel(): UnitModel {
        return UnitModel(
            name = this.name,
            displaySingular = this.displaySingular,
            displayPlural = this.displayPlural,
            abbreviation = this.abbreviation
        )
    }


}

