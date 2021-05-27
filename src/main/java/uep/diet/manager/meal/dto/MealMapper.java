package uep.diet.manager.meal.dto;

import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.domain.Quantity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        String imgLink = meal.getImgLink();
        List<Quantity> quantities = meal.getQuantities();

        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(id);
        mealDTO.setName(name);
        mealDTO.setIngredients(ingredientList
                .stream()
                .map(IngredientMapper::toDto)
                .collect(Collectors.toList()));
        mealDTO.setImgLink(imgLink);

        if (quantities == null || (quantities.size() != ingredientList.size())) {

            if (quantities == null) {
                quantities = new ArrayList<>();
            }

            if (ingredientList.isEmpty()) {
                mealDTO.setQuantities(Collections.emptyList());
            } else {
                int sizeDifference = quantities.size() - ingredientList.size();

                if (sizeDifference < 0) {
                    for (int i = 0; i < Math.abs(sizeDifference); i++) {
                        quantities.add(Quantity.of(0, meal));
                    }
                }

            }
        }

        List<Long> ingredientsIds = ingredientList.stream().map(Ingredient::getIngredientId).collect(Collectors.toList());

        mealDTO.setQuantities(quantities.stream()
                .map(MealMapper::quantityToDTO)
                .collect(Collectors.toList()));
        List<QuantityDTO> quantityDTOS = mealDTO.getQuantities();

        for (int i = 0; i < ingredientsIds.size(); i++) {
            quantityDTOS.get(i).setIngredientId(ingredientsIds.get(i));
        }

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
        meal.setImgLink(mealDTO.getImgLink());

        return meal;
    }

    public static QuantityDTO quantityToDTO(Quantity quantity) {
        Long id = quantity.getQuantityId();
        Integer grams = quantity.getGrams();

        QuantityDTO quantityDTO = new QuantityDTO();
        quantityDTO.setQuantity(grams);
        quantityDTO.setId(id);

        return quantityDTO;
    }

}
