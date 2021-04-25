package uep.diet.manager.meal.dto;

import lombok.Data;
import uep.diet.manager.ingredient.dto.IngredientDTO;

import java.util.List;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
@Data
public class MealDTO {
    private String name;
    private Long id;
    private List<IngredientDTO> ingredients;
}
