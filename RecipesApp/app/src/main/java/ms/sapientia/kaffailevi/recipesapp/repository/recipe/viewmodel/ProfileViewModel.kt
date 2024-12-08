package ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.RecipeRepository
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {
    private val _myRecipeList = MutableLiveData<List<RecipeModel>>()
    val myRecipeList: LiveData<List<RecipeModel>> get() = _myRecipeList

    private val _savedRecipesList = MutableLiveData<List<RecipeModel>>()
    val savedRecipesList: LiveData<List<RecipeModel>> get() = _savedRecipesList


    fun loadMyRecipes() {
        viewModelScope.launch {
            val recipes = withContext(Dispatchers.IO) {
                recipeRepository.getMyRecipes()
            }
            _myRecipeList.postValue(recipes)
        }
    }

    fun loadSavedRecipes() {
        viewModelScope.launch {
            val recipes = withContext(Dispatchers.IO) {
                recipeRepository.getSavedRecipesFromLocalDB()
            }
            _savedRecipesList.postValue(recipes)
        }
    }




}