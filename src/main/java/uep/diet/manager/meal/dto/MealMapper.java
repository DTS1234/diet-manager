package uep.diet.manager.meal.dto;

import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.domain.Quantity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
public class MealMapper {

    public static MealDTO toDTO(Meal meal) {
        String name = meal.getName();
        List<Ingredient> ingredientList = meal.getIngredients();
        Long id = meal.getMealId();

        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(id);
        mealDTO.setName(name);
        mealDTO.setIngredients(ingredientList
                .stream()
                .map(IngredientMapper::toDto)
                .collect(Collectors.toList()));

        return mealDTO;
    }

    public static Meal toEntity(MealDTO mealDTO) {
        String name = mealDTO.getName();
        List<IngredientDTO> ingredientList = mealDTO.getIngredients();
        Long id = mealDTO.getId();

        Meal meal = new Meal();
        meal.setMealId(id);
        meal.setName(name);
        meal.setIngredients(ingredientList
                .stream()
                .map(IngredientMapper::toEntity)
                .collect(Collectors.toList()));

        return meal;
    }

    public static QuantityDTO quantityToDTO(Quantity quantity) {
        Long id = quantity.getQuantityId();
        Integer grams = quantity.getGrams();
        List<Meal> meals = quantity.getMeals();

        QuantityDTO quantityDTO = new QuantityDTO();
        quantityDTO.setQuantity(grams);
        quantityDTO.setMeals(meals.stream().map(MealMapper::toDTO).collect(Collectors.toList()));
        quantityDTO.setId(id);

        return quantityDTO;
    }

}
