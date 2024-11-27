package ms.sapientia.kaffailevi.recipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.FragmentNewRecipeBinding
import ms.sapientia.kaffailevi.recipesapp.databinding.IngredientInputRowItemBinding
import ms.sapientia.kaffailevi.recipesapp.databinding.InstructionInputRowItemBinding
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.ComponentModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.IngredientModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.InstructionModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.MeasurementModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.UnitModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.NewRecipeViewModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.RecipeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NewRecipeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var instructionBindingList: MutableList<InstructionInputRowItemBinding> =
        mutableListOf()
    private var ingredientBindingList: MutableList<IngredientInputRowItemBinding> = mutableListOf()

    private val recipeViewModel: NewRecipeViewModel by viewModels()

    private lateinit var binding: FragmentNewRecipeBinding;
    private lateinit var ingredientsContainer: LinearLayout;
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

        this.binding = FragmentNewRecipeBinding.inflate(inflater, container, false)

        this.ingredientsContainer = binding.ingredientsLinearLayout

        addIngredientRow(this.ingredientsContainer)

        addInstructionInputRow(binding.instructionsLinearLayout)

        binding.saveButton.setOnClickListener{
            saveRecipe()
            recipeViewModel.recipeList.observe(viewLifecycleOwner){
                Log.d("XXX", it.toString())
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun addInstructionInputRow(container: LinearLayout) {
        val rowBinding = InstructionInputRowItemBinding.inflate(layoutInflater, container, false)
        rowBinding.instructionSerialTextView.text =
            getString(R.string.instruction, container.childCount.toString())


        binding.addInstructionButton.setOnClickListener {
            if (rowBinding.instructionEditText.text.isNotEmpty())
                addInstructionInputRow(container)
        }
        container.addView(rowBinding.root)
        instructionBindingList.add(rowBinding)
    }


    private fun addIngredientRow(container: LinearLayout) {
        // Inflate a new ingredient_input_row_item
        val rowBinding = IngredientInputRowItemBinding.inflate(layoutInflater, container, false)

        // Set up a listener to add a new row when the current one is filled

        val onClickFunction = { it: View ->
            if (rowBinding.ingredientNameEditText.text.isNotEmpty() && rowBinding.ingredientQuantityEditText.text.isNotEmpty() && rowBinding.ingredientUnitSpinner.selectedItemPosition != 0) {
                // Check if this is the last row in the container
                if (container.indexOfChild(rowBinding.root) == container.childCount - 1) {
                    // Add a new row
                    addIngredientRow(container)
                }
            }
        }

//        rowBinding.ingredientNameEditText.setOnFocusChangeListener(onClickFunction)
//        rowBinding.ingredientQuantityEditText.setOnFocusChangeListener(onClickFunction)
//        rowBinding.ingredientUnitSpinner.setOnFocusChangeListener(onClickFunction)
        binding.addIngredientButton.setOnClickListener(onClickFunction)
        // Add the row to the container
        container.addView(rowBinding.root)
        ingredientBindingList.add(rowBinding)
    }

    private fun parseInputFieldsToRecipe(): RecipeModel {
        val recipeTitle = binding.titleEditText.text.toString()
        val recipeDescription = binding.descriptionEditText.text.toString()
        val recipeImageUrl = binding.imageUrlEditText.text.toString()
        val recipeVideoUrl = binding.videoUrlEditText.text.toString()
        val recipeComponents = ingredientBindingList.mapIndexed { index, it ->
            ComponentModel(
                it.ingredientNameEditText.text.toString()
                        + " " + it.ingredientQuantityEditText.text.toString()
                        + " " + it.ingredientUnitSpinner.selectedItem.toString(),
                "",
                IngredientModel(it.ingredientNameEditText.text.toString()),
                MeasurementModel(
                    it.ingredientQuantityEditText.text.toString(),
                    UnitModel(it.ingredientUnitSpinner.selectedItem.toString(), "", "", "")
                ), index.toLong()
            )
        }
        val recipeInstructions = instructionBindingList.mapIndexed { index, it ->
            InstructionModel(it.instructionEditText.text.toString(), index.toLong())
        }

        return RecipeModel(
            recipeTitle,
            recipeDescription,
            recipeImageUrl,
            "",
            false,
            "",
            recipeVideoUrl,
            "",
            1,
            recipeComponents,
            recipeInstructions
        )

    }

    private fun saveRecipe() {
        val recipe = parseInputFieldsToRecipe()
        this.recipeViewModel.saveRecipe(recipe)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewRecipeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = NewRecipeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}