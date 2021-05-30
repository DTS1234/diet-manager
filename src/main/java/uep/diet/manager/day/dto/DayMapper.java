package uep.diet.manager.day.dto;

import uep.diet.manager.day.domain.data.Day;
import uep.diet.manager.day.domain.exception.DayAddException;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.MealMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
public class DayMapper {

    private DayMapper(){}

    public static Day toEntity(DayDTO dayDTO){

        String date = dayDTO.getDate();
        Long id = dayDTO.getId();
        List<MealDTO> dtoMeals = dayDTO.getMeals();
        List<Meal> meals;

        if (dtoMeals != null){
            meals= dtoMeals.stream().map(MealMapper::toEntity).collect(Collectors.toList());
        }else {
            meals = Collections.emptyList();
        }

        try {

            Day day = new Day();
            day.setDate(LocalDate.parse(date));
            day.setDayId(id);
            day.setMeals(meals);

            return day;
        }
        catch (DateTimeParseException exception)
        {
            throw new DayAddException("Date must be in format like: YYYY-MM-DD");
        }

    }

    public static DayDTO toDTO(Day day) {

        LocalDate localDate = day.getDate();
        String stringDate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        Long id = day.getDayId();

        List<Meal> meals = day.getMeals();
        List<MealDTO> mealDTOS = meals.stream().map(MealMapper::toDTO).collect(Collectors.toList());

        DayDTO dayDTO = new DayDTO();
        dayDTO.setDate(stringDate);
        dayDTO.setId(id);
        dayDTO.setMeals(mealDTOS);

        return dayDTO;
    }


}
