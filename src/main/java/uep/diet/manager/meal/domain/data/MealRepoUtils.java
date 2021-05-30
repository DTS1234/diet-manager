package uep.diet.manager.meal.domain.data;

import lombok.RequiredArgsConstructor;
import uep.diet.manager.meal.domain.exception.MealNotFoundException;

import java.util.Optional;

/**
 * @author akazmierczak
 * @date 25.04.2021
 */
@RequiredArgsConstructor
public class MealRepoUtils {

    private final MealRepository mealRepository;

    public Meal findByMealByIdOrThrow(Long mealId)
    {
        Optional<Meal> optionalMeal = mealRepository.findById(mealId);
        if (optionalMeal.isPresent())
        {
            return optionalMeal.get();
        }else
        {
            throw new MealNotFoundException("Meal with id of " + mealId + " does not exists");
        }
    }

}
