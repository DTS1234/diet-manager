package uep.diet.manager.ingredient.domain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author akazmierczak
 * @date 05.04.2021
 */
@Slf4j
public class IngredientWrongParametersException extends RuntimeException {

    public IngredientWrongParametersException() {
        super();
    }

    public IngredientWrongParametersException(String message) {
        super(message);
    }
}
