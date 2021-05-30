package uep.diet.manager.meal.domain.service;

import lombok.extern.slf4j.Slf4j;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.exception.MealNotFoundException;

import java.util.Optional;

/**
 * @author akazmierczak
 * @date 29.03.2021
 */
@Slf4j
class DeleteMealTransaction {

    void execute(MealRepository mealRepository, Long id)
    {
        Optional<Meal> optionalMeal = mealRepository.findById(id);
        if (optionalMeal.isPresent()){
            mealRepository.delete(optionalMeal.get());
        }else {
            throw new MealNotFoundException();
        }

    }

}
