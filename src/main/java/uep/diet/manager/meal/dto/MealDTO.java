package uep.diet.manager.meal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uep.diet.manager.ingredient.dto.IngredientDTO;

import java.util.List;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MealDTO {

    private String name;
    private Long id;
    private List<IngredientDTO> ingredients;
    @JsonProperty("imageLink")
    private String imgLink;
    private String mealType;
}
