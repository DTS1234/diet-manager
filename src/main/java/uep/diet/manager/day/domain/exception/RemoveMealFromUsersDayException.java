package uep.diet.manager.day.domain.exception;

/**
 * @author akazmierczak
 * @date 25.04.2021
 */
public class RemoveMealFromUsersDayException extends RuntimeException {

    public RemoveMealFromUsersDayException(String s) {
    }

    public RemoveMealFromUsersDayException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoveMealFromUsersDayException() {
    }
}
