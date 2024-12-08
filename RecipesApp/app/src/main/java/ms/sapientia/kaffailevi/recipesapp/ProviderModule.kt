package ms.sapientia.kaffailevi.recipesapp

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.RecipeDatabase
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dao.RecipeDao
import ms.sapientia.kaffailevi.recipesapp.service.RecipeApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context, RecipeDatabase::class.java, "recipe_database"
        ).build()
    }

    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao {
        return database.recipeDao()
    }
    @Provides
    @Singleton
    fun provideRecipeApiClient(): RecipeApiClient {
        return RecipeApiClient()
    }




}