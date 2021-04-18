package uep.diet.manager.meal.domain;

import lombok.extern.slf4j.Slf4j;
import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.domain.IngredientRepository;
import uep.diet.manager.ingredient.domain.IngredientWrongParametersException;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.dto.MealDTO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 28.03.2021
 */
@Slf4j
class CreateMealTransaction {

    public Meal execute(MealDTO mealDTO, MealRepository mealRepository, IngredientRepository ingredientRepository) {
        Meal mealToBeSaved = new Meal();
        mealToBeSaved.setName(mealDTO.getName());
        mealToBeSaved.setMealId(mealDTO.getId());

        List<Ingredient> ingredientList = createIngredientsIfTheyNotExist(mealDTO, ingredientRepository);

        log.info("Saving meal " + mealToBeSaved.toString());
        Meal savedMeal = mealRepository.save(mealToBeSaved);

        log.info("Updating meal with ingredient data.");
        updateMealForIngredients(savedMeal, ingredientList, mealRepository, ingredientRepository);

        return savedMeal;
    }

    private List<Ingredient> createIngredientsIfTheyNotExist(MealDTO meal, IngredientRepository ingredientRepository) {

        if (meal.getIngredients() != null) {

            List<Ingredient> ingredientList =
                    meal.getIngredients().stream().map(IngredientMapper::toEntity).collect(Collectors.toList());

            //save those that do not exist
            ingredientList
                    .stream()
                    .filter(ingredient -> ingredient.getIngredientId() != null)
                    .filter(ingredient -> !ingredientRepository.existsById(ingredient.getIngredientId()))
                    .forEach(ingredient -> {

                        boolean nameIsNullOrEmpty = ingredient.getName() == null || ingredient.getName().trim().equals("");

                        if (nameIsNullOrEmpty) {
                            throw new IngredientWrongParametersException("Newly created ingredient should at least have a name.");
                        } else {
                            log.info("Creating new ingredient for meal " + meal.getName() + " <-> " + ingredient.getName());
                            ingredientRepository.save(ingredient);
                        }

                    });

            return ingredientList;
        }
        return Collections.emptyList();
    }


    private void updateMealForIngredients(Meal savedMeal, List<Ingredient> ingredients, MealRepository mealRepository, IngredientRepository ingredientRepository) {

        // update meal first
        if (ingredients == null){
            savedMeal.setIngredients(Collections.emptyList());
        } else {
            savedMeal.setIngredients(ingredients);
        }

        mealRepository.save(savedMeal);

        // here update ingredients
        if (ingredients == null)
        {
            ingredients = new ArrayList<>();//if null initialize empty list
        }
        ingredients.forEach(ingredient ->
        {
            List<Meal> mealsWithIngredient = ingredient.getMeal();
            if (mealsWithIngredient == null) // if meals null replace with empty list, then update
            {
                mealsWithIngredient = new ArrayList<>();
            }

            mealsWithIngredient.add(savedMeal);
            ingredient.setMeal(mealsWithIngredient);
            ingredientRepository.save(ingredient);
        });

    }

}
