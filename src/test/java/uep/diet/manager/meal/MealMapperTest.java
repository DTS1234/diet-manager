package uep.diet.manager.meal;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import uep.diet.manager.TestIngredient;
import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.Quantity;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.MealMapper;
import uep.diet.manager.meal.quantities.TestQuantities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @date 29.05.2021
 */
class MealMapperTest {


    @Test
    void shouldMapMealCorrectly_nullQuantites() {

        Meal meal = new Meal();
        meal.setMealId(1L);
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithId(1L),
                TestIngredient.basicWithId(2L)
        ));
        meal.setName("Test name");
        meal.setQuantities(null);

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));

        assertThat(actual.getQuantities())
                .isEqualTo(TestQuantities.defaultQuantitiesDTOList(meal.getIngredients().stream().map(Ingredient::getIngredientId).collect(Collectors.toList())));

    }

    @Test
    void shouldMapMealCorrectly_emptyQuantities() {

        Meal meal = new Meal();
        meal.setMealId(1L);
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithId(1L),
                TestIngredient.basicWithId(2L)
        ));
        meal.setName("Test name");
        meal.setQuantities(Collections.emptyList());

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));

        assertThat(actual.getQuantities())
                .isEqualTo(TestQuantities.defaultQuantitiesDTOList(meal.getIngredients().stream().map(Ingredient::getIngredientId).collect(Collectors.toList())));

    }

    @Test
    void shouldMapMealCorrectly_fullQuantities() {

        Meal meal = new Meal();
        meal.setMealId(1L);
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithId(1L),
                TestIngredient.basicWithId(2L)
        ));
        meal.setName("Test name");
        meal.setQuantities(Arrays.asList(Quantity.of(10, meal), Quantity.of(12, meal)));

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));

        assertThat(actual.getQuantities())
                .isEqualTo(TestQuantities.quantitesDTOForMeal(meal, 10, 12));

    }

    @Test
    void shouldMapMealCorrectly_oneQuantityTwoIngredients() {

        Meal meal = new Meal();
        meal.setMealId(1L);
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithId(1L),
                TestIngredient.basicWithId(2L)
        ));
        meal.setName("Test name");
        meal.setQuantities(new ArrayList<Quantity>(Collections.singletonList(Quantity.of(10, meal))));

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));

        assertThat(actual.getQuantities())
                .isEqualTo(TestQuantities.quantitesDTOForMeal(meal, 10, 0));

    }

    @Test
    void shouldMapMealCorrectly_oneIngredientTwoQuantites() {

        Meal meal = new Meal();
        meal.setMealId(1L);
        meal.setIngredients(Collections.singletonList(
                TestIngredient.basicWithId(1L)
        ));
        meal.setName("Test name");
        meal.setQuantities(new ArrayList<Quantity>(Arrays.asList(Quantity.of(34, meal), Quantity.of(23, meal))));

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Collections.singletonList(
                TestIngredient.basicWithIdDTO(1L)));

        assertThat(actual.getQuantities())
                .isEqualTo(TestQuantities.quantitesDTOForMeal(meal, 34));

    }


}
