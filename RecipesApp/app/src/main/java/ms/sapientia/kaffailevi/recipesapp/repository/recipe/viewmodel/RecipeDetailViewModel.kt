package ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.RecipeRepository
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeDetailModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel

class RecipeDetailViewModel: ViewModel() {
    private val _recipeDetail = MutableLiveData<RecipeDetailModel>()
    val recipe : LiveData<RecipeDetailModel> get() = _recipeDetail

    private val repository = RecipeRepository

    fun loadRecipeDetail(context: Context) {
        _recipeDetail.value = repository.getDetail(context)
    }



}