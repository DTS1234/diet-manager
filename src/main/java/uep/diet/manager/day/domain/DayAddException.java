package uep.diet.manager.day.domain;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
public class DayAddException extends RuntimeException {

    public DayAddException() {
        super();
    }

    public DayAddException(String message) {
        super(message);
    }

    public DayAddException(String message, Throwable cause) {
        super(message, cause);
    }
}
