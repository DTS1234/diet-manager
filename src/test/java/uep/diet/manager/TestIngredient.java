package uep.diet.manager;

import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.dto.IngredientDTO;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
public class TestIngredient {

    public static Ingredient basicWithName(String name)
    {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setCaloriesPer100g(67);
        ingredient.setFat(12);
        ingredient.setProtein(14);
        ingredient.setCarbohydrates(43);
        return ingredient;
    }

    public static IngredientDTO basicWithIdDTO(Long id)
    {
        IngredientDTO ingredient = new IngredientDTO();
        ingredient.setId(id);
        ingredient.setName("Test " + id);
        ingredient.setCaloriesPer100g(67);
        ingredient.setFat(12);
        ingredient.setProtein(14);
        ingredient.setCarbohydrates(43);
        return ingredient;
    }

}
