package uep.diet.manager.user.domain;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
