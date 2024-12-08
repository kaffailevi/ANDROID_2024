package ms.sapientia.kaffailevi.recipesapp.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class Util {

    companion object{
        inline fun <reified T> parseJson(jsonString: String): T? {
            return try {
                val gson = Gson()
                val type = object : TypeToken<T>() {}.type // This specifies the correct type at runtime
                gson.fromJson<T>(jsonString, type)
            } catch (e: JsonSyntaxException) {
                Log.e("JSON Parsing Error", "Error parsing JSON: ${e.message}")
                null
            }
        }
    }
}