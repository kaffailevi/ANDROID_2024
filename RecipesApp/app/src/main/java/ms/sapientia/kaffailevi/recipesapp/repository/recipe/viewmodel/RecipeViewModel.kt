package ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel

import android.content.Context
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


    fun loadInstructionData(context: Context) {

        viewModelScope.launch {
            val recipeList = recipeRepository.getAll(context)
            _recipeList.postValue(recipeList)
        }
    }
}