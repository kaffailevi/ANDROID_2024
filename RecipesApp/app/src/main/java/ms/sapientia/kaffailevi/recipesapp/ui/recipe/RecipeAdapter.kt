package ms.sapientia.kaffailevi.recipesapp.ui.recipe

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.RecipeRowItemBinding
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel

class RecipeAdapter(
    private var dataSet: List<RecipeModel>,
    private val onItemClick: (RecipeModel) -> Unit,
    private val onFavClick: (RecipeModel) -> Unit,
    private val isRecipeSaved: (RecipeModel) -> LiveData<Boolean>,
    private val onLongClickFunction: (RecipeModel) -> Boolean
) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val savedStateMap = mutableMapOf<Long, Boolean>()


    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recipeNameTextView: TextView
        var recipeImageView: ImageView
        var recipeDescriptionTextView: TextView
        var favButton: ImageButton
        val binding = RecipeRowItemBinding.bind(view)

        init {
            recipeNameTextView = binding.recipeTitleTextView
            recipeImageView = binding.recipeImage
            recipeDescriptionTextView = binding.recipeDescriptionTextView
            favButton = binding.favButton
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_row_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.dataSet.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = this.dataSet[position]

        holder.recipeNameTextView.text = this.dataSet[position].name
        holder.recipeDescriptionTextView.text = this.dataSet[position].description
        holder.binding.root.setOnClickListener {
            onItemClick(recipe)
        }
        isRecipeSaved(recipe).observeForever { isSaved ->
            savedStateMap[recipe.recipeId] = isSaved
            val favIconRes = if (isSaved) R.drawable.heart else R.drawable.love
            holder.favButton.setImageResource(favIconRes)
        }
        holder.favButton.setOnClickListener {
            onFavClick(recipe)
            val newState = !(savedStateMap[recipe.recipeId] ?: false)
            savedStateMap[recipe.recipeId] = newState
            val favIconRes = if (newState) R.drawable.heart else R.drawable.love
            holder.favButton.setImageResource(favIconRes)

        }
        holder.binding.root.setOnLongClickListener {
           onLongClickFunction(recipe)
        }
        val progressBar = holder.binding.loadingSpinner // Reference the ProgressBar from the layout

// Show the ProgressBar while loading the image
        progressBar.visibility = View.VISIBLE

        Glide.with(holder.itemView.context)
            .load(recipe.thumbnailUrl) // Replace with the correct URL field
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
            .into(holder.recipeImageView)

    }

    fun updateRecipes(recipes: List<RecipeModel>) {
        this.dataSet = recipes
        this.notifyDataSetChanged()
    }
}