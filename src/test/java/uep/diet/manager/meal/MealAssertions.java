package uep.diet.manager.meal;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import uep.diet.manager.meal.domain.data.Meal;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
public class MealAssertions extends AbstractAssert<MealAssertions, Meal> {

    private MealAssertions(Meal meal, Class<?> selfType) {
        super(meal, selfType);
    }

    public static MealAssertions assertThat(Meal actual)
    {
        return new MealAssertions(actual, MealAssertions.class);
    }

    public MealAssertions hasMealId(Long mealId)
    {
        Assertions.assertThat(actual.getMealId())
                .as("meal id should be equal to " + mealId)
                .isEqualTo(mealId);
        return this;
    }


    public MealAssertions hasName(String expectedName)
    {
        Assertions.assertThat(actual.getName())
                .as("meal should contain name like: " + expectedName)
                .isEqualTo(expectedName);
        return this;
    }

    public MealAssertions hasIngredientWithName(String name, int index)
    {
        Assertions.assertThat(actual.getIngredients().get(index).getName())
                .isEqualTo(name);
        return this;
    }

}
