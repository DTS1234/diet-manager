package uep.diet.manager.meal.dto;

import lombok.Data;

/**
 * @author akazmierczak
 * @date 25.04.2021
 */
@Data
public class MealCaloriesDTO {

    private Long mealId;
    private String name;
    private Integer calories;

}
