package ms.sapientia.kaffailevi.recipesapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.FragmentRecipesBinding
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.RecipeViewModel
import ms.sapientia.kaffailevi.recipesapp.ui.recipe.RecipeAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RecipesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRecipesBinding;
    private val recipeViewModel: RecipeViewModel by viewModels()
    private var initialShowState = true

    private lateinit var recipeList: MutableList<RecipeModel>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    private fun navigateToRecipeDetail(recipe: RecipeModel): Unit {
        Log.d("CLICK", "Recipe clicked: ${recipe.name}")
        val bundle = Bundle()
        bundle.putLong("recipeID", recipe.recipeId)
        findNavController().navigate(R.id.action_recipesFragment_to_recipeDetailFragment, bundle)

    }


    private fun observeCurrentList(adapter: RecipeAdapter) {
        val recipeLiveData: LiveData<List<RecipeModel>> =
            if (initialShowState) recipeViewModel.myRecipeList else recipeViewModel.recipeList

        recipeLiveData.observe(viewLifecycleOwner) { recipes ->
            adapter.updateRecipes(recipes) // Update the adapter with the new list
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Initialize the binding object
        binding = FragmentRecipesBinding.inflate(inflater, container, false)

        // Set up the RecyclerView
        val recyclerView = binding.myRecipesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        val detailFunction: (recipe: RecipeModel) -> Unit = { recipe ->
            navigateToRecipeDetail(recipe)
        }

        val favFunction: (recipe: RecipeModel) -> Unit = { recipe ->
            addRecipeToFavs(recipe)
        }
        val onLongClickFunction: (recipe: RecipeModel) -> Boolean =
            if (!initialShowState) { _ -> false } else { recipe ->
                try {
                    AlertDialog.Builder(this.requireContext())
                        .setTitle("Delete Recipe")
                        .setMessage("Are you sure you want to delete this recipe?")
                        .setPositiveButton("Yes")
                        { dialog, _ ->
                            recipeViewModel.deleteRecipeFromApi(recipe)

                            recipeViewModel.isRecipeSaved(recipe)
                                .observe(viewLifecycleOwner) { isSaved ->
                                    if (isSaved) {
                                        recipeViewModel.deleteRecipeFromLocalDB(recipe)
                                    }
                                }
                            Toast.makeText(
                                this.requireContext(),
                                "Recipe deleted successfully.",
                                Toast.LENGTH_SHORT
                            ).show()
                            recipeViewModel.loadRecipeData()
                            recipeViewModel.loadMyRecipeData()
                            dialog.dismiss()
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create().show()

                    true
                } catch (e: Exception) {
                    Log.e("DELETE", "Error deleting recipe: ${e.message}")
                    Toast.makeText(
                        this.requireContext(),
                        "Failed to delete recipe.",
                        Toast.LENGTH_SHORT
                    ).show()

                    false
                }
            }
        val adapter = RecipeAdapter(listOf(), detailFunction, favFunction, { recipe ->
            recipeViewModel.isRecipeSaved(recipe)
        }, onLongClickFunction)
        recyclerView.adapter = adapter


        // Function to observe the correct list based on the toggle state


        // Set up button listeners to toggle the state and update the observed list
        binding.ownButton.setOnClickListener {
            initialShowState = true
            binding.ownButton.setTextColor(resources.getColor(R.color.white, resources.newTheme()))
            binding.savedButton.setTextColor(
                resources.getColor(
                    R.color.gray_text, resources.newTheme()
                )
            )
            recipeViewModel.loadMyRecipeData()

            observeCurrentList(adapter)
        }

        binding.savedButton.setOnClickListener {
            initialShowState = false
            binding.ownButton.setTextColor(
                resources.getColor(
                    R.color.gray_text, resources.newTheme()
                )
            )
            binding.savedButton.setTextColor(
                resources.getColor(
                    R.color.white, resources.newTheme()
                )
            )
            recipeViewModel.loadRecipeData()
            observeCurrentList(adapter)
        }

        // Observe the initial list
        observeCurrentList(adapter)

        // Load data into the ViewModel
        recipeViewModel.loadRecipeData()
        recipeViewModel.loadMyRecipeData()
        return binding.root
    }

    private fun addRecipeToFavs(recipe: RecipeModel): Unit {
        recipeViewModel.isRecipeSaved(recipe).observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                // Remove from favorites
                recipeViewModel.deleteRecipeFromLocalDB(recipe)
            } else {
                // Add to favorites
                recipeViewModel.saveRecipeToLocalDB(recipe)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecipesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = RecipesFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}