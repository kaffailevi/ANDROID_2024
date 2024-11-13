package ms.sapientia.kaffailevi.recipesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ms.sapientia.kaffailevi.recipesapp.databinding.FragmentRecipesBinding
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
class RecipesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRecipesBinding;
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Initialize the binding object
        binding = FragmentRecipesBinding.inflate(inflater, container, false)

        // Set the RecyclerView layout manager
        val recyclerView = binding.recipesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        // Observe the recipeList and update the adapter when data changes
        recipeViewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            recyclerView.adapter = RecipeAdapter(recipes)
        }

        // Load data into the view model
        recipeViewModel.loadInstructionData(this.requireContext())

        // Return the root view of the binding
        return binding.root
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