package uep.diet.manager.meal.domain;

import lombok.extern.slf4j.Slf4j;

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
