package uep.diet.manager.meal.domain.exception;

/**
 * @date 27.05.2021
 */
public class QuantityException extends RuntimeException{

    public QuantityException() {
    }

    public QuantityException(String message) {
        super(message);
    }
}
