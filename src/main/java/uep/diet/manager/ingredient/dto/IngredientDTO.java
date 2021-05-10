package uep.diet.manager.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uep.diet.manager.meal.dto.MealDTO;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class IngredientDTO {
    private Long id;
    private String name;
    private int caloriesPer100g;
    @JsonIgnore
    private MealDTO mealDTO;
    private int fat;
    private int protein;
    private int carbohydrates;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int quantityInGrams;
}
