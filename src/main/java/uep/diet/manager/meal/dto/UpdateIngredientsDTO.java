package uep.diet.manager.meal.dto;

import lombok.Data;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientDTOList;

import java.util.List;

/**
 * @date 30.05.2021
 */
@Data
public class UpdateIngredientsDTO {
    private List<IngredientDTO> ingredients;
}
