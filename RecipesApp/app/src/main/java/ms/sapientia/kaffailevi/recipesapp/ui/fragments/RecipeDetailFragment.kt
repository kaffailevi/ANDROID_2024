package ms.sapientia.kaffailevi.recipesapp.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.ui.res.colorResource
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.FragmentRecipeDetailBinding
import ms.sapientia.kaffailevi.recipesapp.databinding.NutritionItemBinding
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeDetailModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.RecipeDetailViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val viewModel: RecipeDetailViewModel by viewModels()
    private lateinit var recipeDetailModel: RecipeDetailModel
    private lateinit var binding: FragmentRecipeDetailBinding
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


        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        val recipeID: Long? = arguments?.getLong("recipeID")

        if (recipeID == null) {
            Toast.makeText(requireContext(), "Invalid recipe ID", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return this.binding.root
        }

        viewModel.loadRecipeDetail(recipeID)
        Log.d("RecipeDetail", "Recipe detail id: $recipeID")
        viewModel.recipe.observe(viewLifecycleOwner) {
            recipeDetailModel = it
            bindData(it)
            Log.d("XXX", recipeDetailModel.toString())
        }

        // Inflate the layout for this fragment
        return this.binding.root
    }

    @OptIn(UnstableApi::class)
    private fun bindData(recipeDetail: RecipeDetailModel) {
        binding.recipeDetailName.text = recipeDetail.name
        binding.recipeDetailDescription.text = recipeDetail.description

        val componentsContainer = binding.componentsContainer
        val componentStrings = recipeDetail.components.sortedBy { it.position }.map {
            try{
                if (it.measurement.quantity.toInt() >= 2)
                    "${it.measurement.quantity} ${it.measurement.unit.displayPlural} ${it.ingredient.name}"
                else "${it.measurement.quantity} ${it.measurement.unit.displaySingular} ${it.ingredient.name}"
            }
            catch (e: NumberFormatException){
                "${it.measurement.quantity} ${it.measurement.unit.abbreviation} ${it.ingredient.name}"
            }
     }

        componentStrings.forEach {
            val componentTextView = TextView(requireContext())

            componentTextView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            componentTextView.setPadding(16, 15, 16, 15)
            componentTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            componentTextView.textSize = 16f
            componentTextView.setTextColor(resources.getColor(R.color.white))
            componentTextView.text = it
            componentsContainer.addView(componentTextView)
        }


        binding.recipeDetailInstructions.text =
            recipeDetail.instructions.sortedBy { it.position }.map {
                it.position.toString() + ". " + it.displayText
            }.joinToString("\n\n")


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        val player = ExoPlayer.Builder(requireContext()).build()
        binding.videoView.player = player
        val mediaItem = MediaItem.fromUri(recipeDetail.originalVideoUrl)
// Set the media item to be played.
        player.setMediaItem(mediaItem)
// Prepare the player.
        player.prepare()
// Start the playback.
        val progressBar = binding.loadingSpinner
        Glide.with(binding.recipeDetailImage.context)
            .load(recipeDetail.thumbnailUrl.toUri())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    // Hide the ProgressBar if the image fails to load
                    progressBar.visibility = View.GONE
                    return false // Allow Glide to handle setting the error placeholder
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    // Hide the ProgressBar when the image is ready
                    progressBar.visibility = View.GONE
                    return false // Allow Glide to set the image on the ImageView
                }
            }).error(R.drawable.broken_image)
            .into(binding.recipeDetailImage)

        val nutritionItemBinding = NutritionItemBinding.inflate(layoutInflater)

        binding.nutritionContainer.addView(nutritionItemBinding.root)

        nutritionItemBinding.fatValue.text = recipeDetail.nutrition.fat.toString()
        nutritionItemBinding.carbohydratesValue.text =
            recipeDetail.nutrition.carbohydrates.toString()
        nutritionItemBinding.proteinValue.text = recipeDetail.nutrition.protein.toString()
        nutritionItemBinding.caloriesValue.text = recipeDetail.nutrition.calories.toString()
        nutritionItemBinding.sugarValue.text = recipeDetail.nutrition.sugar.toString()
        nutritionItemBinding.fiberValue.text = recipeDetail.nutrition.sugar.toString()


    }


    override fun onPause() {
        super.onPause()
        binding.videoView.player?.stop()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecipeDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = RecipeDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}