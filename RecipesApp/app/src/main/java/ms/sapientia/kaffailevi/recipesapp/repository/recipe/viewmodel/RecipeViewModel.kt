package ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.RecipeRepository
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel
@Inject constructor(private val recipeRepository: RecipeRepository) : ViewModel() {
    private val _recipeList = MutableLiveData<List<RecipeModel>>()
    val recipeList: LiveData<List<RecipeModel>> get() = _recipeList

    private val _myRecipeList = MutableLiveData<List<RecipeModel>>()
    val myRecipeList: LiveData<List<RecipeModel>> get() = _myRecipeList


    fun loadRecipeData() {
        viewModelScope.launch {
            val recipeList = recipeRepository.getSavedRecipesFromLocalDB()
            _recipeList.postValue(recipeList)
        }
    }

    fun loadMyRecipeData() {
        viewModelScope.launch {
            val recipeList = recipeRepository.getMyRecipes()
            _myRecipeList.postValue(recipeList)
        }
    }



    fun saveRecipeToLocalDB(recipe: RecipeModel) {
        viewModelScope.launch {
            recipeRepository.insertRecipeToLocalDb(recipe)

        }
    }

    fun isRecipeSaved(recipe: RecipeModel): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.postValue(recipeRepository.isRecipeSavedLocally(recipe))
        }
        return result
    }

    fun deleteRecipeFromLocalDB(recipe: RecipeModel) {
        viewModelScope.launch {
            recipeRepository.deleteRecipeFromLocalDb(recipe.recipeId)
        }
    }

    fun deleteRecipeFromApi(recipeModel: RecipeModel){
        viewModelScope.launch {
            Log.d("DELETE", "Recipe to be deleted: ${recipeModel.name}")
            recipeRepository.deleteRecipeFromApi(recipeModel.recipeId)
        }
    }

}