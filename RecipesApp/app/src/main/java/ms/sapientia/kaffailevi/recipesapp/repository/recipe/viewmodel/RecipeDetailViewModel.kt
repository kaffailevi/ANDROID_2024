package ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.RecipeRepository
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeDetailModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {
    private val _recipeDetail = MutableLiveData<RecipeDetailModel>()
    val recipe : LiveData<RecipeDetailModel> get() = _recipeDetail



    fun loadRecipeDetail(recipeId: Long) {

        viewModelScope.launch {
            _recipeDetail.postValue(repository.getRecipeDetail(recipeId))
        }

    }



}