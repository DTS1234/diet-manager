package uep.diet.manager.day.domain.data;

import lombok.RequiredArgsConstructor;
import uep.diet.manager.day.domain.exception.DayNotFoundException;
import uep.diet.manager.meal.domain.data.Meal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 25.04.2021
 */
@RequiredArgsConstructor
public class DayRepoUtils {

    private final DayRepository dayRepository;

    public Day findDayByIdOrThrow(Long dayId) {
        Optional<Day> optionalDay = dayRepository.findById(dayId);

        if (optionalDay.isPresent()) {
            return optionalDay.get();
        } else {
            throw new DayNotFoundException("There is no day with id : " + dayId);
        }
    }

    public boolean mealAlreadyAssignedToDay(Long dayId, Long mealId)
    {
        Day dayFound = findDayByIdOrThrow(dayId);
        List<Meal> meals = dayFound.getMeals();
        List<Long> mealIds = meals.stream().map(Meal::getMealId).collect(Collectors.toList());
        return mealIds.contains(mealId);
    }


}
