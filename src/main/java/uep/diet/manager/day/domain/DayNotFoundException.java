package uep.diet.manager.day.domain;

/**
 * @author akazmierczak
 * @date 23.04.2021
 */
public class DayNotFoundException extends RuntimeException {
    public DayNotFoundException() {
    }

    public DayNotFoundException(String message) {
        super(message);
    }

    public DayNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
