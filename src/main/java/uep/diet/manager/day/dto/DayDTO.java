package uep.diet.manager.day.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uep.diet.manager.meal.dto.MealDTO;

import java.util.List;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayDTO {
    private String date;
    private Long id;
    private List<MealDTO> meals;
}
