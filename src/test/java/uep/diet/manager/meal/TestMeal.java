package uep.diet.manager.meal;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
public class TestMeal extends Meal{

    static Meal basic()
    {
        Meal meal = new Meal();
        meal.setMealId(1L);
        return meal;
    }

}
