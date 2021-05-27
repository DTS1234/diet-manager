package uep.diet.manager.meal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import uep.diet.manager.meal.domain.Meal;

import java.util.List;

/**
 * @author akazmierczak
 * @date 03.05.2021
 */
@Data
public class QuantityDTO {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private MealDTO meal;
    private Integer quantity;
    private Long ingredientId;

}
