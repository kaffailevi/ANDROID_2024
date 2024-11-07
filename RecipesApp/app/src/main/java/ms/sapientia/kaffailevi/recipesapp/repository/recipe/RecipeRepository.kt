package ms.sapientia.kaffailevi.recipesapp.repository.recipe

import android.content.Context
import com.google.gson.Gson
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import java.io.IOException

object RecipeRepository {
    private val gson = Gson()
    private val recipeList = mutableListOf<RecipeModel>()
    init {
//        Read from json and parse with gson


    }
    private fun readJsonFromAssets(context: Context, fileName:String):String?{
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        }catch (ex: IOException){
            ex.printStackTrace()
            return null
        }
    }
}