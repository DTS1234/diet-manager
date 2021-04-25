package uep.diet.manager.meal.domain;

import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.domain.IngredientRepository;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.MealMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author akazmierczak
 * @date 29.03.2021
 */
class UpdateMealTransaction {

    Meal execute(Long id, MealRepository mealRepository, IngredientRepository ingredientRepository, MealDTO newMeal) {
        Meal newMealEntity = MealMapper.toEntity(newMeal);
        Meal savedMeal;
        Optional<Meal> optionalMeal = mealRepository.findById(id);

        if (optionalMeal.isPresent()) {

            Meal oldMeal = optionalMeal.get();
            newMealEntity.setMealId(oldMeal.getMealId());

            savedMeal = mealRepository.save(newMealEntity);

            List<Ingredient> savedMealIngredients = savedMeal.getIngredients();

            if (savedMealIngredients == null) {
                savedMealIngredients = new ArrayList<>();
            }

            savedMealIngredients
                    .forEach(ingredient ->
                    {
                        List<Meal> ingredientMeals = ingredient.getMeal();

                        if (ingredientMeals == null) {
                            ingredientMeals = new ArrayList<>();
                        }

                        ingredientMeals.add(savedMeal);
                        ingredient.setMeal(ingredientMeals);
                        ingredientRepository.save(ingredient);
                    });

            return savedMeal;
        }

        throw new MealNotFoundException(id);
    }

}
