package uep.diet.manager.ingredient.dto;

import uep.diet.manager.ingredient.domain.Ingredient;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
public class IngredientMapper {

    public static IngredientDTO toDto(Ingredient ingredient)
    {
        Long ingredientId = ingredient.getIngredientId();
        int calories = ingredient.getCaloriesPer100g();
        String name = ingredient.getName();
        int fat = ingredient.getFat();
        int protein = ingredient.getProtein();
        int carbs = ingredient.getCarbohydrates();

        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setCaloriesPer100g(calories);
        ingredientDTO.setName(name);
        ingredientDTO.setId(ingredientId);
        ingredientDTO.setCarbohydrates(carbs);
        ingredientDTO.setFat(fat);
        ingredientDTO.setProtein(protein);

        return ingredientDTO;
    }

    public static Ingredient toEntity(IngredientDTO ingredientDTO)
    {
        Long id = ingredientDTO.getId();
        int calories = ingredientDTO.getCaloriesPer100g();
        String name = ingredientDTO.getName();
        int fat = ingredientDTO.getFat();
        int protein = ingredientDTO.getProtein();
        int carbs = ingredientDTO.getCarbohydrates();

        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(id);
        ingredient.setCaloriesPer100g(calories);
        ingredient.setName(name);
        ingredient.setCarbohydrates(carbs);
        ingredient.setFat(fat);
        ingredient.setProtein(protein);

        return ingredient;
    }

}
