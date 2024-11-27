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
class NewRecipeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) : ViewModel(){
    private val _recipeList = MutableLiveData<List<RecipeModel>>()
    val recipeList: LiveData<List<RecipeModel>> get() = _recipeList

    fun saveRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            recipeRepository.insertRecipeToLocalDb(recipe)
            refreshRecipeList()
        }
    }

    private suspend fun refreshRecipeList(){
        _recipeList.postValue( recipeRepository.getMyRecipes())
    }



}