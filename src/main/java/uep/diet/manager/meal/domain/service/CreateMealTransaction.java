package uep.diet.manager.meal.domain.service;

import lombok.extern.slf4j.Slf4j;
import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.ingredient.domain.exception.IngredientNotFoundException;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.exception.MealCreationException;
import uep.diet.manager.meal.dto.MealDTO;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 28.03.2021
 */
@Slf4j
class CreateMealTransaction {

    private final MealDTO mealDTO;
    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    public CreateMealTransaction(MealDTO mealDTO, MealRepository mealRepository, IngredientRepository ingredientRepository) {
        this.mealDTO = mealDTO;
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Meal execute() {

        List<IngredientDTO> passedIngredients = mealDTO.getIngredients() == null ? Collections.emptyList() : mealDTO.getIngredients();
        String passedName = mealDTO.getName();
        String passedImgLink = mealDTO.getImgLink() == null ? "" : mealDTO.getImgLink();
        String type = mealDTO.getMealType() == null ? "" : mealDTO.getMealType();
        checkName(passedName);

        Meal mealToBeSaved = new Meal();
        mealToBeSaved.setName(passedName);
        mealToBeSaved.setImgLink(passedImgLink);
        mealToBeSaved.setMealType(type);

        List<Ingredient> existingIngredients = getExistingIngredientsByIds(passedIngredients);
        List<Ingredient> newIngredients = getNewIngredientsFromDTOBody(passedIngredients);

        List<Ingredient> ingredientsToBeSaved = new ArrayList<>();
        ingredientsToBeSaved.addAll(existingIngredients);
        ingredientsToBeSaved.addAll(newIngredients);

        Meal savedMeal = mealRepository.save(mealToBeSaved);
        ingredientsToBeSaved.forEach(ingredient -> ingredient.setMeal(savedMeal));

        List<Ingredient> collect = ingredientsToBeSaved.stream().map(ingredientRepository::save).collect(Collectors.toList());
        mealToBeSaved.setIngredients(collect);

        return savedMeal;
    }

    private List<Ingredient> getNewIngredientsFromDTOBody(List<IngredientDTO> passedIngredients) {

        if (passedIngredients.isEmpty()) {
            return Collections.emptyList();
        }

        List<Ingredient> newIngredients = new ArrayList<>();

        passedIngredients.stream()
                .filter(ingredient -> ingredient.getId() == null || !ingredientRepository.existsById(ingredient.getId()))
                .forEach(ingredientDTO ->
                        newIngredients.add(ingredientRepository.save(IngredientMapper.toEntity(ingredientDTO)))
                );

        return newIngredients;
    }

    private List<Ingredient> getExistingIngredientsByIds(List<IngredientDTO> passedIngredients) {

        if (passedIngredients.isEmpty()) {
            return Collections.emptyList();
        }

        List<Ingredient> existingIngredients = new ArrayList<>();

        passedIngredients.stream()
                .map(IngredientDTO::getId)
                .filter(Objects::nonNull)
                .filter(ingredientRepository::existsById)
                .forEach(id ->
                        existingIngredients.add(ingredientRepository.findById(id).orElseThrow(IngredientNotFoundException::new))
                );

        log.info("Found following ingredients by id's " + existingIngredients);
        return existingIngredients;
    }

    private void checkName(String passedName) {
        if (passedName == null || passedName.isEmpty()) {
            throw new MealCreationException("Meal name cannot be empty !");
        }
    }

}
