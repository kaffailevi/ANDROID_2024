package ms.sapientia.kaffailevi.recipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.FragmentHomeBinding
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.HomeViewModel
import ms.sapientia.kaffailevi.recipesapp.ui.recipe.RecipeAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var recipeList: List<RecipeModel> = emptyList<RecipeModel>()
    private lateinit var binding: FragmentHomeBinding;
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: RecipeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val detailFunction: (recipe: RecipeModel) -> Unit = { recipe ->
            navigateToRecipeDetail(recipe)
        }

        val favFunction: (recipe: RecipeModel) -> Unit = { recipe ->
            addRecipeToFavs(recipe)
        }

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = RecipeAdapter(
            listOf(), detailFunction, favFunction,
            { recipe ->
                viewModel.isRecipeSaved(recipe)
            },
            onLongClickFunction = { _ -> false },
        )
        recyclerView.adapter = adapter
        viewModel.updateRecipesFromApi()
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            adapter.updateRecipes(recipes)
            recipeList = recipes
        }

        binding.searchButton.setOnClickListener {
            onSearchButtonClick()
        }
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->

            // Handle the action (e.g., process input)
            onSearchButtonClick()
            // Hide the keyboard
            val imm = getSystemService(
                requireContext(),
                InputMethodManager::class.java
            ) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)

            // Optionally clear the focus
            binding.searchEditText.clearFocus()

            true // Indicate the action was handled

            // Let the system handle other actions

        }


        return binding.root
    }

    private fun addRecipeToFavs(recipe: RecipeModel): Unit {
        viewModel.isRecipeSaved(recipe).observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                // Remove from favorites
                viewModel.deleteRecipeFromLocalDB(recipe)
            } else {
                // Add to favorites
                viewModel.saveRecipeToLocalDB(recipe)
            }
        }
    }

    private fun navigateToRecipeDetail(recipe: RecipeModel): Unit {
        Log.d("CLICK", "Recipe clicked: ${recipe.name}")
        val bundle = Bundle()
        bundle.putLong("recipeID", recipe.recipeId)
        findNavController().navigate(R.id.action_homeFragment_to_recipeDetailFragment, bundle)
    }

    private fun onSearchButtonClick() {
        val searchInput = binding.searchEditText.text.toString()
        if (searchInput.isNotEmpty()) {
            val recipesFiltered = recipeList.filter { recipe ->
                recipe.name.contains(searchInput, ignoreCase = true)
                        || recipe.keywords.contains(searchInput, ignoreCase = true)
            }
            adapter.updateRecipes(recipesFiltered)
        } else viewModel.updateRecipesFromApi()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

}