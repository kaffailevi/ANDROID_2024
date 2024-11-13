package ms.sapientia.kaffailevi.recipesapp.ui.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel

class RecipeAdapter(private val dataSet: List<RecipeModel>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recipeNameTextView: TextView
        var recipeImageView: ImageView
        var recipeDescriptionTextView: TextView

        init {
            recipeNameTextView = view.findViewById(R.id.recipeTitleTextView)
            recipeImageView = view.findViewById(R.id.recipeImage)
            recipeDescriptionTextView = view.findViewById(R.id.recipeDescriptionTextView)
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
        holder.recipeNameTextView.text = this.dataSet[position].name
        holder.recipeDescriptionTextView.text = this.dataSet[position].description
    }
}