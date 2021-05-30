package uep.diet.manager.meal.quantities;

import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.meal.TestMeal;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.domain.Quantity;
import uep.diet.manager.meal.dto.MealMapper;
import uep.diet.manager.meal.dto.QuantityDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 29.05.2021
 */
public class TestQuantities {

    public static List<Quantity> defaultQuantitiesList(List<Long> ingredientsIds, Meal meal) {

        List<Quantity> quantities = new ArrayList<>();

        for (int i = 0; i < ingredientsIds.size(); i++) {
            Quantity quantity = new Quantity();
            quantity.setMeal(meal);
            quantity.setGrams(0);
            quantity.setQuantityId((long) i);
            quantities.add(quantity);
        }

        return quantities;
    }

    public static List<QuantityDTO> defaultQuantitiesDTOList(List<Long> ingredientsIds) {

        List<QuantityDTO> quantities = new ArrayList<>();

        for (Long ingredientsId : ingredientsIds) {
            QuantityDTO quantity = new QuantityDTO();
            quantity.setQuantity(0);
            quantity.setIngredientId(ingredientsId);
            quantities.add(quantity);
        }

        return quantities;
    }

    public static List<QuantityDTO> quantitesDTOForMeal(Meal meal, int ...quantites) {

        List<Ingredient> ingredients = meal.getIngredients();
        List<QuantityDTO> quantityDTOS = new ArrayList<>();

        for (int i = 0; i < ingredients.size(); i++) {
            QuantityDTO quantityDTO = new QuantityDTO();
            quantityDTO.setQuantity(quantites[i]);
            quantityDTO.setIngredientId(ingredients.get(i).getIngredientId());
            quantityDTOS.add(quantityDTO);
        }

        return quantityDTOS;
    }

}
