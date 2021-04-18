package uep.diet.manager.meal.dto;

import lombok.Data;
import uep.diet.manager.meal.domain.Meal;

import java.util.List;

/**
 * @author akazmierczak
 * @date 17.04.2021
 */
@Data
public class MealListDTO {

    private List<MealDTO> meals;

}
