package uep.diet.manager.day.domain.exception;

/**
 * @author akazmierczak
 * @date 23.04.2021
 */
public class MealAddToDayException extends RuntimeException {

    public MealAddToDayException() {
        super();
    }

    public MealAddToDayException(String message) {
        super(message);
    }

    public MealAddToDayException(String message, Throwable cause) {
        super(message, cause);
    }
}
