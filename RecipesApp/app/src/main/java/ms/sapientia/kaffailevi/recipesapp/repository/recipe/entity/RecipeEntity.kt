package ms.sapientia.kaffailevi.recipesapp.repository.recipe.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val internalId: Long = 0L, // Room will handle generating this ID
    @SerializedName("json_data")
    val json: String
)