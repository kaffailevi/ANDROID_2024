package ms.sapientia.kaffailevi.recipesapp.repository.recipe

import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.ComponentDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.IngredientDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.InstructionDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.MeasurementDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.NutritionDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.RecipeDetailDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.dto.UnitDTO
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.ComponentModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.IngredientModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.InstructionModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.MeasurementModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.NutritionModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeDetailModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.RecipeModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.model.UnitModel


fun RecipeDTO.toModel(): RecipeModel {
    return RecipeModel(
        name = this.name.toString(),
        description = this.description.toString(),
        thumbnailUrl = this.thumbnailUrl.toString(),
        keywords = this.keywords.toString(),
        isPublic = this.isPublic ?: false,
        userEmail = this.userEmail.toString(),
        originalVideoUrl = this.originalVideoUrl.toString(),
        country = this.country.toString(),
        numServings = this.numServings!!,
        components = this.components?.toComponentModelList()?:emptyList(),
        instructions = this.instructions?.toInstructionModelList()?:emptyList(),
        recipeId = this.recipeID!!
    )
}


fun RecipeDetailDTO.toModel(): RecipeDetailModel {
    return RecipeDetailModel(
        name = this.name ?: "No name",
        description = this.description ?: "No description",
        thumbnailUrl = this.thumbnailUrl.toString(),
        keywords = this.keywords.toString(),
        isPublic = this.isPublic ?: false,
        userEmail = this.userEmail ?: "Unknown",
        originalVideoUrl = this.originalVideoUrl ?: "",
        country = this.country ?: "Unknown",
        numServings = this.numServings ?: 0L,
        components = this.components?.toComponentModelList() ?: emptyList(),
        instructions = this.instructions?.toInstructionModelList() ?: emptyList(),
        nutrition = this.nutrition?.toModel() ?: NutritionModel(0L, 0L, 0L, 0L, 0L, 0L),
        recipeID = this.recipeID ?: 0L
    )
}

fun RecipeDetailModel.toDTO(): RecipeDetailDTO {
    return RecipeDetailDTO(
        recipeID = this.recipeID,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        keywords = this.keywords,
        isPublic = this.isPublic,
        userEmail = this.userEmail,
        originalVideoUrl = this.originalVideoUrl,
        country = this.country,
        numServings = this.numServings,
        components = this.components.toComponentDTOList(),
        instructions = this.instructions.toInstructionDTOList(),
        nutrition = this.nutrition.toNutritionDTO()
    )
}

fun NutritionModel.toNutritionDTO(): NutritionDTO? {
    return NutritionDTO(
        calories = this.calories,
        protein = this.protein,
        fat = this.fat,
        carbohydrates = this.carbohydrates,
        sugar = this.sugar,
        fiber = this.fiber
    )
}

fun List<ComponentModel>.toComponentDTOList(): List<ComponentDTO>? {
    return this.map { it.toDTO() }
}


fun List<InstructionModel>.toInstructionDTOList(): List<InstructionDTO>? {
    return this.map { it.toDTO() }
}

fun ComponentModel.toDTO(): ComponentDTO {
    return ComponentDTO(
        rawText = this.rawText,
        extraComment = this.extraComment,
        ingredient = this.ingredient.toDTO(),
        measurement = this.measurement.toDTO(),
        position = this.position
    )

}

fun IngredientModel.toDTO(): IngredientDTO {
    return IngredientDTO(
        name = this.name
    )
}

fun MeasurementModel.toDTO(): MeasurementDTO {
    return MeasurementDTO(
        quantity = this.quantity, unit = this.unit.toDTO()
    )
}

fun UnitModel.toDTO(): UnitDTO {
    return UnitDTO(
        name = this.name,
        displaySingular = this.displaySingular,
        displayPlural = this.displayPlural,
        abbreviation = this.abbreviation
    )
}

fun InstructionModel.toDTO(): InstructionDTO {
    return InstructionDTO(
        position = this.position, displayText = this.displayText
    )

}


fun NutritionDTO.toModel(): NutritionModel {
    return NutritionModel(
        calories = this.calories ?: 0,
        protein = this.protein ?: 0,
        fat = this.fat ?: 0,
        carbohydrates = this.carbohydrates ?: 0,
        sugar = this.sugar ?: 0,
        fiber = this.fiber ?: 0
    )
}


fun MeasurementDTO.toModel(): MeasurementModel {
    return MeasurementModel(
        quantity = this.quantity.toString(), unit = this.unit?.toModel() ?: UnitModel(
            "Unknown unit",
            displaySingular = "",
            displayPlural = "",
            abbreviation = ""
        )
    )
}


fun InstructionDTO.toModel(): InstructionModel {
    return InstructionModel(
        position = this.position?:0, displayText = this.displayText.toString()
    )
}

fun IngredientDTO.toModel(): IngredientModel {
    return IngredientModel(
        name = this.name.toString()
    )
}

fun ComponentDTO.toModel(): ComponentModel {
    return ComponentModel(
        rawText = this.rawText.toString(),
        extraComment = this.extraComment.toString(),
        ingredient = this.ingredient?.toModel() ?: IngredientModel("Unknown ingredient"),
        measurement = this.measurement?.toModel() ?: MeasurementModel(
            "0", UnitModel(
                "Unknown unit",
                displaySingular = "",
                displayPlural = "",
                abbreviation = ""
            )
        ),
        position = this.position ?: 0
    )
}

fun List<ComponentDTO?>.toComponentModelList(): List<ComponentModel> {

    return this.filterNotNull().map { it: ComponentDTO ->
        val model: ComponentModel = it.toModel()
        model
    }
}

fun List<InstructionDTO?>.toInstructionModelList(): List<InstructionModel> {
    return this.filterNotNull().map { it.toModel() }
}

fun List<MeasurementDTO>.toMeasurementModelList(): List<MeasurementModel> {
    return this.map { it.toModel() }
}

fun List<NutritionDTO>.toNutritionModelList(): List<NutritionModel> {
    return this.map { it.toModel() }
}

fun List<RecipeDTO>.toRecipeModelList(): List<RecipeModel> {
    return this.map { it.toModel() }
}

fun UnitDTO.toModel(): UnitModel {
    return UnitModel(
        name = this.name.toString(),
        displaySingular = this.displaySingular.toString(),
        displayPlural = this.displayPlural.toString(),
        abbreviation = this.abbreviation.toString()
    )
}


