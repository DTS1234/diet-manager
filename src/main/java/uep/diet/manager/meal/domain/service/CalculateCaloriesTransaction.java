package uep.diet.manager.meal.domain.service;

import lombok.RequiredArgsConstructor;
import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepoUtils;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.dto.MealCaloriesDTO;

import java.util.List;

/**
 * @author akazmierczak
 * @date 25.04.2021
 */
@RequiredArgsConstructor
public class CalculateCaloriesTransaction {

    private final MealRepository mealRepository;

    MealCaloriesDTO execute(Long mealId) {

        MealRepoUtils mealRepoUtils = new MealRepoUtils(mealRepository);
        Meal mealFound = mealRepoUtils.findByMealByIdOrThrow(mealId);
        MealCaloriesDTO mealCaloriesDTO = new MealCaloriesDTO();

        mealCaloriesDTO.setMealId(mealId);
        mealCaloriesDTO.setName(mealFound.getName());

        List<Ingredient> ingredientList = mealFound.getIngredients();

        if (ingredientList == null || ingredientList.isEmpty()) {
            mealCaloriesDTO.setCalories(0);
        } else {
            int calories = ingredientList.stream()
                    .map(Ingredient::getCaloriesPer100g)
                    .reduce(0, Integer::sum);
            mealCaloriesDTO.setCalories(calories);
        }

        return mealCaloriesDTO;

    }

}
