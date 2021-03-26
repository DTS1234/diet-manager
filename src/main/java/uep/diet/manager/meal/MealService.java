package uep.diet.manager.meal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
@Service
@RequiredArgsConstructor
public class MealService implements AddMealUseCase {

    private final MealRepository mealRepository;

    @Override
    public void addMeal(Meal meal) {
        mealRepository.save(meal);
    }
}
