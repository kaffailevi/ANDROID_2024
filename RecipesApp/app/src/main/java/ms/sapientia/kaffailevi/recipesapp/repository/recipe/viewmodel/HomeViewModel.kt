package ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel

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
class HomeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {
    private val _recipes: MutableLiveData<List<RecipeModel>> = MutableLiveData()
    val recipes: MutableLiveData<List<RecipeModel>> get() = _recipes


    fun updateRecipesFromApi() {
        viewModelScope.launch {
            _recipes.postValue(recipeRepository.getAllFromAPI())
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

}

