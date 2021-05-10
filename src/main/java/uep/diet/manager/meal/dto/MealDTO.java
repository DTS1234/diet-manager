package uep.diet.manager.meal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.meal.domain.Quantity;

import java.util.List;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MealDTO {
    private String name;
    private Long id;
    private List<IngredientDTO> ingredients;
    private List<QuantityDTO> quantities;
}
