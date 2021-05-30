package uep.diet.manager.meal.domain.exception;

import lombok.Getter;

/**
 * @author akazmierczak
 * @date 29.03.2021
 */
public class MealNotFoundException extends RuntimeException {

    @Getter
    private Long idWithWasNotFound;

    public MealNotFoundException(String message) {
        super(message);
    }

    public MealNotFoundException() {
        super();
    }

    public MealNotFoundException(Long idWithWasNotFound) {
        this.idWithWasNotFound = idWithWasNotFound;
    }
}
