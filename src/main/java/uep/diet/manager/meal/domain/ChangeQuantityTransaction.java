package uep.diet.manager.meal.domain;

import lombok.RequiredArgsConstructor;
import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.domain.IngredientNotFoundException;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.MealMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 27.05.2021
 */
@RequiredArgsConstructor
class ChangeQuantityTransaction {

    private final MealRepository mealRepository;

    public MealDTO execute(Integer newQuantity, Long mealId, Long ingredientId) {

        checkQuantity(newQuantity);

        Meal meal = mealRepository.findById(mealId).orElseThrow(MealNotFoundException::new);
        List<Ingredient> ingredients = meal.getIngredients();
        int ingredientIndex = getIngredientIndex(ingredientId, ingredients);
        List<Quantity> quantities = meal.getQuantities() == null ? new ArrayList<>(): meal.getQuantities();

        if (quantities.size() == ingredients.size())
        {
            quantities.set(ingredientIndex, Quantity.of(newQuantity, meal));
            meal.setQuantities(quantities);
            mealRepository.save(meal);
            return MealMapper.toDTO(meal);
        }

        if (quantities.isEmpty()) {
            fillEmptyQuantities(newQuantity, meal, ingredients, ingredientIndex, quantities);
            meal.setQuantities(quantities);
            mealRepository.save(meal);
        }

        // ingredients should have quantities assigned (should be same size)
        if (quantities.size() != ingredients.size()) {
            adjustQuantitesToIngredients(newQuantity, meal, ingredients, ingredientIndex, quantities);
            mealRepository.save(meal);
            return MealMapper.toDTO(meal);
        }

        return MealMapper.toDTO(meal);
    }

    private void adjustQuantitesToIngredients(Integer newQuantity, Meal meal, List<Ingredient> ingredients, int ingredientIndex, List<Quantity> quantities) {
        int difference = ingredients.size() - quantities.size();

        // if ingredients size is greater add quantities with zero grams to make lists equal
        boolean thereIsMoreIngredientsThanQuantities = difference > 0;
        if (thereIsMoreIngredientsThanQuantities) {
            addQuantitiesToMakeListsEqual(meal, quantities, difference);
            // now change the element to passed value
            quantities.set(ingredientIndex, Quantity.of(newQuantity, meal));
            meal.setQuantities(quantities);

        } else {
            List<Quantity> newQuantities = new ArrayList<>(quantities); // changing to modifiable list
            // if more quantities than ingredients delete quantities to make lists equal
            removeQuantitiesToMakeListsEqual(ingredients, newQuantities);
            // now set new value to specified ingredient
            newQuantities.set(ingredientIndex, Quantity.of(newQuantity, meal));
            meal.setQuantities(newQuantities);
        }
    }

    private void removeQuantitiesToMakeListsEqual(List<Ingredient> ingredients, List<Quantity> newQuantities) {
        while (newQuantities.size() != ingredients.size()) {
            newQuantities.remove(newQuantities.size() - 1);
        }
    }

    private void addQuantitiesToMakeListsEqual(Meal meal, List<Quantity> quantities, int difference) {
        for (int i = 0; i < difference; i++) {
            quantities.add(Quantity.of(0, meal));
        }
    }

    private void fillEmptyQuantities(Integer newQuantity, Meal meal, List<Ingredient> ingredients, int ingredientIndex, List<Quantity> quantities) {
        for (int i = 0; i < ingredients.size(); i++) {

            // add new quantity if ingredient index matches quantity index
            if (i == ingredientIndex) {
                Quantity quantity = Quantity.of(newQuantity, meal);
                quantities.add(quantity);
            } // else add 0 by default
            else {
                quantities.add(Quantity.of(0, meal));
            }

        }
    }

    private int getIngredientIndex(Long ingredientId, List<Ingredient> ingredients) {
        List<Ingredient> filteredIngredients = ingredients.stream()
                .filter(ingredient -> ingredient.getIngredientId().equals(ingredientId))
                .collect(Collectors.toList());

        if (filteredIngredients.isEmpty()) {
            throw new IngredientNotFoundException();
        }

        Ingredient ingredientFound = filteredIngredients.get(0);

        return ingredients.indexOf(ingredientFound);
    }

    private void checkQuantity(Integer newQuantity) {
        if (newQuantity < 0)
        {
            throw new QuantityException("Quantity cannot be negative!");
        }
    }

}
