package uep.diet.manager.meal.domain.service;

import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.ingredient.domain.exception.IngredientNotFoundException;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.data.Quantity;
import uep.diet.manager.meal.domain.exception.MealNotFoundException;
import uep.diet.manager.meal.domain.exception.UpdateIngredientsException;
import uep.diet.manager.meal.dto.QuantityDTO;
import uep.diet.manager.meal.dto.UpdateIngredientsDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 30.05.2021
 */
class UpdateMealIngredientTransaction {

    private final IngredientRepository ingredientRepository;
    private final MealRepository mealRepository;
    private final Long mealId;
    private final UpdateIngredientsDTO body;


    public UpdateMealIngredientTransaction(IngredientRepository ingredientRepository, MealRepository mealRepository, Long mealId, UpdateIngredientsDTO body) {
        this.ingredientRepository = ingredientRepository;
        this.mealRepository = mealRepository;
        this.mealId = mealId;
        this.body = body;
    }

    UpdateIngredientsDTO execute() {
        List<IngredientDTO> passedIngredients = body.getIngredientDTOList() == null ? new ArrayList<>() : body.getIngredientDTOList();
        List<QuantityDTO> passedQuantities = body.getQuantityListDTO() == null ? new ArrayList<>() : body.getQuantityListDTO();

        if (passedIngredients.size() != passedQuantities.size()) {
            throw new UpdateIngredientsException("Quantities should have same size as ingredients!");
        }

        //new objects that will be filled up and returned as a new body
        List<IngredientDTO> newIngredients = new ArrayList<>();
        List<QuantityDTO> newQuantities = new ArrayList<>();

        setUpIngredients(passedIngredients, newIngredients);
        setQuantities(passedQuantities, newQuantities);

        UpdateIngredientsDTO result = new UpdateIngredientsDTO();
        result.setIngredientDTOList(newIngredients);
        result.setQuantityListDTO(newQuantities);

        return result;
    }

    private void setQuantities(List<QuantityDTO> passedQuantities, List<QuantityDTO> newQuantities) {
        Meal meal = mealRepository.findById(mealId).orElseThrow(MealNotFoundException::new);
        List<Quantity> list = new ArrayList<>();

        if (passedQuantities == null || passedQuantities.isEmpty()) {
            meal.setQuantities(Collections.emptyList());
            mealRepository.save(meal);
            return;
        }

        for (QuantityDTO q : passedQuantities) {
            Integer grams = q.getQuantity();
            Quantity quantityToBeAdded = new Quantity();
            quantityToBeAdded.setMeal(meal);
            quantityToBeAdded.setGrams(grams);
            list.add(quantityToBeAdded);
        }

        meal.setQuantities(list);

        Meal savedMeal = mealRepository.save(meal);

        List<Long> newQuantityIds = savedMeal.getQuantities().stream().map(Quantity::getQuantityId).collect(Collectors.toList());
        List<Long> ingredientsIds = savedMeal.getIngredients().stream().map(Ingredient::getIngredientId).collect(Collectors.toList());

        for (int i = 0; i < newQuantityIds.size(); i++) {
            passedQuantities.get(i).setId(newQuantityIds.get(i));
        }

        for (int i = 0; i < ingredientsIds.size(); i++) {
            passedQuantities.get(i).setIngredientId(ingredientsIds.get(i));
        }

        newQuantities.addAll(passedQuantities);
    }

    private void setUpIngredients(List<IngredientDTO> passedIngredients, List<IngredientDTO> newIngredients) {

        Meal meal = mealRepository.findById(mealId).orElseThrow(MealNotFoundException::new);
        meal.setIngredients(Collections.emptyList());
        meal.setMealId(mealId);
        Meal savedMeal = mealRepository.save(meal);

        List<Ingredient> ingredientsToBeSaved = passedIngredients.stream().map(IngredientMapper::toEntity).collect(Collectors.toList());

        List<Ingredient> foundByMeal = ingredientRepository.findByMeal(meal).orElseThrow(IngredientNotFoundException::new);

        foundByMeal.forEach(ingredient -> {
            List<Meal> meals = ingredient.getMeal();
            if (meals != null) {
                meals.remove(savedMeal);
            }
        });

        List<Ingredient> savedIngredients = new ArrayList<>();
        ingredientsToBeSaved.forEach(ingredient -> {

            List<Meal> meals = ingredient.getMeal();

            if (meals == null) {
                meals = new ArrayList<>();
                meals.add(savedMeal);
                ingredient.setMeal(meals);
            } else {
                List<Meal> currentIngredientMeals = meals.stream().filter(meal1 -> meal1.getMealId().equals(mealId))
                        .collect(Collectors.toList());
                currentIngredientMeals.add(savedMeal);
                ingredient.setMeal(currentIngredientMeals);
            }
            ingredient.setIngredientId(null);

            savedIngredients.add(ingredientRepository.save(ingredient));
            savedMeal.setIngredients(savedIngredients);
            mealRepository.save(savedMeal);
        });

        newIngredients.addAll(savedIngredients.stream().map(IngredientMapper::toDto).collect(Collectors.toList()));
    }

}
