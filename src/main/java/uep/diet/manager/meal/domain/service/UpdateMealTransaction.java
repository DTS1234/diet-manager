package uep.diet.manager.meal.domain.service;

import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.exception.MealNotFoundException;
import uep.diet.manager.meal.dto.MealDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 29.03.2021
 */
class UpdateMealTransaction {

    Meal execute(Long id, MealRepository mealRepository, IngredientRepository ingredientRepository,  MealDTO newMeal) {

        Meal mealToBeUpdated = mealRepository.findById(id).orElseThrow(MealNotFoundException::new);
        mealToBeUpdated = updatedIngredients(mealRepository, newMeal, mealToBeUpdated, ingredientRepository);

        String newMealName = newMeal.getName();
        mealToBeUpdated.setName(newMealName);

        String mealType = newMeal.getMealType();
        mealToBeUpdated.setMealType(mealType);

        String imgLink = newMeal.getImgLink();
        mealToBeUpdated.setImgLink(imgLink);

        return mealRepository.save(mealToBeUpdated);
    }

    private Meal updatedIngredients(MealRepository mealRepository, MealDTO newMeal, Meal mealToBeUpdated, IngredientRepository ingredientRepository) {

        mealToBeUpdated.getIngredients().forEach(ingredient -> ingredient.setMeal(null));

        List<IngredientDTO> newIngredients = newMeal.getIngredients();
        List<Ingredient> ingredientsEntities = newIngredients.stream().map(IngredientMapper::toEntity).collect(Collectors.toList());
        mealToBeUpdated.setIngredients(Collections.emptyList());
        mealRepository.save(mealToBeUpdated);
        mealToBeUpdated.setIngredients(ingredientsEntities);

        Meal save = mealRepository.save(mealToBeUpdated);
        ingredientsEntities.forEach(i -> {i.setMeal(save);ingredientRepository.save(i);});

        return save;
    }

}
