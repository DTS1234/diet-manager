package uep.diet.manager.meal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author akazmierczak
 * @date 03.05.2021
 */
@Data
public class QuantityDTO {

    @JsonIgnore
    private Long id;
    private Integer quantity;
    private Long ingredientId;

    public static QuantityDTO of(Long id, Integer quantity, Long ingredientId)
    {
        QuantityDTO quantityDTO = new QuantityDTO();
        quantityDTO.setQuantity(quantity);
        quantityDTO.setIngredientId(ingredientId);
        quantityDTO.setId(id);
        return quantityDTO;
    }

}
