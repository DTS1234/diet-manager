package uep.diet.manager.ingredient;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import uep.diet.manager.ingredient.domain.data.Ingredient;

/**
 * @author akazmierczak
 * @date 04.04.2021
 */
public class IngredientAssertions extends AbstractAssert<IngredientAssertions, Ingredient> {

    public IngredientAssertions(Ingredient ingredient, Class<?> selfType) {
        super(ingredient, selfType);
    }

    public static IngredientAssertions assertThat(Ingredient actual)
    {
        return new IngredientAssertions(actual, IngredientAssertions.class);
    }

    public IngredientAssertions hasName(String expectedName)
    {
        Assertions.assertThat(actual.getName())
                .as("ingredient should have a name equal to : " + expectedName)
                .isEqualTo(expectedName);
        return this;
    }

    public IngredientAssertions hasFatEqualTo(int expectedFat)
    {
        Assertions.assertThat(actual.getFat())
                .as("ingredient should have fat equal to : " + expectedFat)
                .isEqualTo(expectedFat);
        return this;
    }

    public IngredientAssertions hasCarbsEqualTo(int expectedCarbs)
    {
        Assertions.assertThat(actual.getCarbohydrates())
                .as("ingredient should have carbs equal to : " + expectedCarbs)
                .isEqualTo(expectedCarbs);
        return this;
    }

    public IngredientAssertions hasProteinEqualTo(int expectedProtein)
    {
        Assertions.assertThat(actual.getProtein())
                .as("ingredient should have protein equal to : " + expectedProtein)
                .isEqualTo(expectedProtein);
        return this;
    }

    public IngredientAssertions hasCaloriesEqualTo(int expectedCalories)
    {
        Assertions.assertThat(actual.getCaloriesPer100g())
                .as("ingredient should have fat equal to : " + expectedCalories)
                .isEqualTo(expectedCalories);
        return this;
    }



}
