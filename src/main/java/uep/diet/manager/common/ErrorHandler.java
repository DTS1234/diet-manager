package uep.diet.manager.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uep.diet.manager.ingredient.domain.IngredientNotFoundException;
import uep.diet.manager.ingredient.domain.IngredientWrongParametersException;
import uep.diet.manager.meal.domain.MealNotFoundException;

/**
 * @author akazmierczak
 * @date 05.04.2021
 */
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({MealNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleMealNotFound(Exception exception)
    {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IngredientWrongParametersException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIngredientParameters(Exception exception)
    {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IngredientNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleIngredientNotFound(Exception exception)
    {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
