package uep.diet.manager.meal;

import uep.diet.manager.TestIngredient;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.dto.MealDTO;

import java.util.Arrays;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
public class TestMeal extends Meal {

    static Meal basic() {
        Meal meal = new Meal();
        meal.setName("Pancakes");
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithName("Test 1"),
                TestIngredient.basicWithName("Test 2")));

        return meal;
    }

    static MealDTO basicDTO() {
        MealDTO meal = new MealDTO();
        meal.setId(1L);
        meal.setName("Pancakes");
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));

        return meal;
    }

}
