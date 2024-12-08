package ms.sapientia.kaffailevi.recipesapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.FragmentNewRecipeBinding
import ms.sapientia.kaffailevi.recipesapp.databinding.IngredientInputRowItemBinding
import ms.sapientia.kaffailevi.recipesapp.databinding.InstructionInputRowItemBinding
import ms.sapientia.kaffailevi.recipesapp.databinding.NutritionItemBinding
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.ComponentModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.IngredientModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.InstructionModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.MeasurementModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.NutritionModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeDetailModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.UnitModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.NewRecipeViewModel
import ms.sapientia.kaffailevi.recipesapp.util.Util

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

    private lateinit var nutritionItemBinding: NutritionItemBinding

    private lateinit var possibleIngredientUnits: HashMap<String, UnitModel>

    private lateinit var binding: FragmentNewRecipeBinding;
    private lateinit var ingredientsContainer: LinearLayout;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        lifecycleScope.launch {
            val jsonString =
                requireContext().assets.open("units.json").bufferedReader().use { it.readText() }
            val unitsList = Util.parseJson<List<UnitModel>>(jsonString) ?: emptyList()
            possibleIngredientUnits =
                unitsList.associateBy { it.name } as HashMap<String, UnitModel>
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        this.binding = FragmentNewRecipeBinding.inflate(inflater, container, false)

        this.ingredientsContainer = binding.ingredientsLinearLayout

        addIngredientRow(this.ingredientsContainer)

        addInstructionInputRow(binding.instructionsLinearLayout)

        val nutritionFrameLayout = binding.nutritionFrameLayout
        nutritionItemBinding = NutritionItemBinding.inflate(layoutInflater)
        nutritionFrameLayout.addView(nutritionItemBinding.root)


        binding.saveButton.setOnClickListener {
            saveRecipe()
            recipeViewModel.recipeList.observe(viewLifecycleOwner) {
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
            if (rowBinding.instructionEditText.text.isNotEmpty()) addInstructionInputRow(container)
        }
        container.addView(rowBinding.root)
        instructionBindingList.add(rowBinding)
    }


    private fun addIngredientRow(container: LinearLayout) {
        // Inflate a new ingredient_input_row_item
        val rowBinding = IngredientInputRowItemBinding.inflate(layoutInflater, container, false)

        rowBinding.ingredientUnitTextView.setOnClickListener { unitTV ->
            val builder = AlertDialog.Builder(requireContext())
            val dialog: AlertDialog = builder.setTitle("Select unit")
                .setSingleChoiceItems(
                    possibleIngredientUnits.keys.toTypedArray(),
                    -1
                ) { dialog, which ->
                    (unitTV as TextView).text =
                        possibleIngredientUnits.keys.toTypedArray()[which]
                    dialog.dismiss()
                }.create()
            dialog.show()
        }
        rowBinding.ingredientUnitTextView.text =
            possibleIngredientUnits.keys.toTypedArray()[0]


        val onClickFunction = { it: View ->
            if (rowBinding.ingredientNameEditText.text.isNotEmpty() && rowBinding.ingredientQuantityEditText.text.isNotEmpty()) {
                // Check if this is the last row in the container
                if (container.indexOfChild(rowBinding.root) == container.childCount - 1) {
                    // Add a new row
                    addIngredientRow(container)
                }
            }
        }


        binding.addIngredientButton.setOnClickListener(onClickFunction)
        // Add the row to the container
        container.addView(rowBinding.root)
        ingredientBindingList.add(rowBinding)
    }

    private fun parseInputFieldsToRecipe(): RecipeDetailModel {
        val recipeTitle = binding.titleEditText.text.toString()
        val recipeDescription = binding.descriptionEditText.text.toString()
        val recipeImageUrl = binding.imageUrlEditText.text.toString()
        val recipeVideoUrl = binding.videoUrlEditText.text.toString()
        val nutrition: NutritionModel = NutritionModel(
            calories = nutritionItemBinding.caloriesValue.text.toString().toLong(),
            protein = nutritionItemBinding.proteinValue.text.toString().toLong(),
            fat = nutritionItemBinding.fatValue.text.toString().toLong(),
            carbohydrates = nutritionItemBinding.carbohydratesValue.text.toString().toLong(),
            sugar = nutritionItemBinding.sugarValue.text.toString().toLong(),
            fiber = nutritionItemBinding.fiberValue.text.toString().toLong()
        )
        Log.d("NutritionBinding", nutritionItemBinding.caloriesValue.text.toString())
        val recipeComponents = ingredientBindingList.mapIndexed { index, it ->
            ComponentModel(
                rawText = it.ingredientQuantityEditText.text.toString() + " " + possibleIngredientUnits[it.ingredientUnitTextView.text.toString()] + " of " + it.ingredientNameEditText.text.toString(),
                extraComment = "",
                ingredient = IngredientModel(it.ingredientNameEditText.text.toString()),
                measurement = MeasurementModel(
                    quantity = it.ingredientQuantityEditText.text.toString(),
                    unit = possibleIngredientUnits[it.ingredientUnitTextView.text.toString()]!!
                ),
                position = index.toLong() + 1
            )
        }
        val recipeInstructions = instructionBindingList.mapIndexed { index, it ->
            InstructionModel(
                displayText = it.instructionEditText.text.toString(),
                position = index.toLong() + 1
            )
        }
        Log.d("NutritionInfo", nutrition.toString())
        return RecipeDetailModel(
            recipeID = 0L,
            name = recipeTitle,
            description = recipeDescription,
            thumbnailUrl = recipeImageUrl,
            keywords = recipeComponents.joinToString(" ") { it.ingredient.name },
            isPublic = false,
            userEmail = "",
            originalVideoUrl = recipeVideoUrl,
            country = "RO",
            numServings = 4,
            components = recipeComponents.filter { it.ingredient.name.isNotEmpty() && it.measurement.quantity.isNotEmpty() && it.measurement.unit.name.isNotEmpty() },
            instructions = recipeInstructions.filter { it.displayText.isNotEmpty() },
            nutrition = nutrition

        )

    }

    private fun saveRecipe() {
        val recipe = parseInputFieldsToRecipe()
        this.recipeViewModel.saveRecipe(recipe) {
            findNavController().popBackStack()
        }

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