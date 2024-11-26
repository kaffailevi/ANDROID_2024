package ms.sapientia.kaffailevi.recipesapp.repository.recipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dao.RecipeDao
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.entity.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1, exportSchema =
false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

}
