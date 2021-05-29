package uep.diet.manager.meal.domain;

/**
 * @date 28.05.2021
 */
public class MealCreationException extends RuntimeException {

    public MealCreationException() {
    }

    public MealCreationException(String message) {
        super(message);
    }
}
