package uep.diet.manager.meal;

import uep.diet.manager.ingredient.TestIngredient;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.dto.MealDTO;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
public class TestMeal extends Meal {

    public static Meal basic() {
        Meal meal = new Meal();
        meal.setName("Pancakes");
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithName("Test 1"),
                TestIngredient.basicWithName("Test 2")));

        return meal;
    }

    public static Meal basicWith(String name, String imgLink, String mealType) {
        Meal meal = new Meal();
        meal.setName(name);
        meal.setMealType(mealType);
        meal.setImgLink(imgLink);

        return meal;
    }

    public static Meal basicWith6Ingredients() {
        Meal meal = new Meal();
        meal.setName("Pancakes");
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithName("Test 1"),
                TestIngredient.basicWithName("Test 2"),
                TestIngredient.basicWithName("Test 3"),
                TestIngredient.basicWithName("Test 4"),
                TestIngredient.basicWithName("Test 5"),
                TestIngredient.basicWithName("Test 6")
        ));

        return meal;
    }


    public static Meal emptyIngredients() {
        Meal meal = new Meal();
        meal.setName("Pancakes");
        meal.setIngredients(Collections.emptyList());
        meal.setMealId(1L);

        return meal;
    }

    public static MealDTO basicDTO() {
        MealDTO meal = new MealDTO();
        meal.setId(1L);
        meal.setName("Pancakes");
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));

        return meal;
    }

}
