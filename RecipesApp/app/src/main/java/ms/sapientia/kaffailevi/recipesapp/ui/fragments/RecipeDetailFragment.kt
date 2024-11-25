package ms.sapientia.kaffailevi.recipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ms.sapientia.kaffailevi.recipesapp.databinding.FragmentRecipeDetailBinding
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeDetailModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.RecipeDetailViewModel
import javax.inject.Inject

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
class RecipeDetailFragment: Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    @Inject
     lateinit var viewModel: RecipeDetailViewModel
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
    ): View? {


        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        viewModel.recipe.observe(viewLifecycleOwner) {
            recipeDetailModel = it
            bindData(it)
            Log.d("XXX", recipeDetailModel.toString())
        }
        viewModel.loadRecipeDetail(requireContext())

        // Inflate the layout for this fragment
        return this.binding.root
    }

    @OptIn(UnstableApi::class)
    private fun bindData(recipeDetail: RecipeDetailModel) {
        binding.recipeDetailName.text = recipeDetail.name
        binding.recipeDetailDescription.text = recipeDetail.description
        binding.recipeDetailInstructions.text = recipeDetail.instructions.toString()

        val player = ExoPlayer.Builder(requireContext()).build()
        binding.videoView.player = player
        val mediaItem = MediaItem.fromUri(recipeDetail.originalVideoUrl)
// Set the media item to be played.
        player.setMediaItem(mediaItem)
// Prepare the player.
        player.prepare()
// Start the playback.
        Glide.with(binding.recipeDetailImage.context)
            .load(recipeDetail.thumbnailUrl.toUri())
            .into(binding.recipeDetailImage)



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