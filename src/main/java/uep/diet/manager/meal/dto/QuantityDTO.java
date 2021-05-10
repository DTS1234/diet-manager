package uep.diet.manager.meal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
    private List<MealDTO> meals;
    private Integer quantity;
    private Long ingredientId;

}
