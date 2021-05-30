package uep.diet.manager.day.domain.exception;

/**
 * @author akazmierczak
 * @date 23.04.2021
 */
public class DayNotFoundException extends RuntimeException {
    public DayNotFoundException(String message) {
        super(message);
    }
}
