package uep.diet.manager.ingredient.domain;

/**
 * @author akazmierczak
 * @date 05.04.2021
 */
public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException() {
        super();
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
