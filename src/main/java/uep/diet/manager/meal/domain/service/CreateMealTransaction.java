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
import uep.diet.manager.meal.dto.QuantityDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

        List<QuantityDTO> passedQuantities = mealDTO.getQuantities() == null ? Collections.emptyList() : mealDTO.getQuantities();
        List<IngredientDTO> passedIngredients = mealDTO.getIngredients() == null ? Collections.emptyList() : mealDTO.getIngredients();
        String passedName = mealDTO.getName();
        String passedImgLink = mealDTO.getImgLink() == null ? "" : mealDTO.getImgLink();
        checkName(passedName);

        Meal mealToBeSaved = new Meal();
        mealToBeSaved.setName(passedName);
        mealToBeSaved.setImgLink(passedImgLink);
        changeNullsForEmptyCollections(passedQuantities, passedIngredients, mealToBeSaved);

        List<Ingredient> existingIngredients = getExistingIngredientsByIds(passedIngredients);
        List<Ingredient> newIngredients = getNewIngredientsFromDTOBody(passedIngredients);

        List<Ingredient> ingredientsToBeSaved = new ArrayList<>();
        ingredientsToBeSaved.addAll(existingIngredients);
        ingredientsToBeSaved.addAll(newIngredients);

        ingredientsToBeSaved.forEach(ingredientRepository::save);
        mealToBeSaved.setIngredients(ingredientsToBeSaved);

        return mealRepository.save(mealToBeSaved);
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

    private void changeNullsForEmptyCollections(List<QuantityDTO> passedQuantities, List<IngredientDTO> passedIngredients, Meal mealToBeSaved) {
        if (passedIngredients.isEmpty()) {
            mealToBeSaved.setIngredients(Collections.emptyList());
        }

        if (passedQuantities.isEmpty()) {
            mealToBeSaved.setQuantities(Collections.emptyList());
        }
    }

    private void checkName(String passedName) {
        if (passedName == null || passedName.isEmpty()) {
            throw new MealCreationException("Meal name cannot be empty !");
        }
    }

}
