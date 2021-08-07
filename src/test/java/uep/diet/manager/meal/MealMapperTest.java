package uep.diet.manager.meal;


import org.junit.jupiter.api.Test;
import uep.diet.manager.ingredient.TestIngredient;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.MealMapper;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @date 29.05.2021
 */
class MealMapperTest {


    @Test
    void shouldMapMealCorrectly() {

        Meal meal = new Meal();
        meal.setMealId(1L);
        meal.setIngredients(Arrays.asList(
                TestIngredient.basicWithId(1L),
                TestIngredient.basicWithId(2L)
        ));
        meal.setName("Test name");


        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));

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

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));
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

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));
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

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L)));
    }

    @Test
    void shouldMapMealCorrectly_oneIngredientTwoQuantites() {

        Meal meal = new Meal();
        meal.setMealId(1L);
        meal.setIngredients(Collections.singletonList(
                TestIngredient.basicWithId(1L)
        ));
        meal.setName("Test name");

        MealDTO actual = MealMapper.toDTO(meal);

        assertThat(actual.getName()).isEqualTo("Test name");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getIngredients()).isEqualTo(Collections.singletonList(
                TestIngredient.basicWithIdDTO(1L)));
    }


}
