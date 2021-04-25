package uep.diet.manager.meal.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.domain.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.dto.MealCaloriesDTO;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.MealListDTO;
import uep.diet.manager.meal.dto.MealMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MealService {

    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    public MealDTO createMeal(MealDTO mealDTO) {
        CreateMealTransaction createMealTransaction = new CreateMealTransaction();
        Meal createdMeal = createMealTransaction.execute(mealDTO, mealRepository, ingredientRepository);
        return MealMapper.toDTO(createdMeal);
    }

    public void deleteMeal(Long id) {
        DeleteMealTransaction deleteMealTransaction = new DeleteMealTransaction();
        deleteMealTransaction.execute(mealRepository, id);
    }

    public MealDTO updateMeal(Long id, MealDTO newMealDTO) {
        UpdateMealTransaction updateMealTransaction = new UpdateMealTransaction();
        Meal updatedMeal = updateMealTransaction.execute(id, mealRepository, ingredientRepository, newMealDTO);
        return MealMapper.toDTO(updatedMeal);
    }

    public MealDTO getById(Long id) {
        Optional<Meal> meal = mealRepository.findById(id);

        if (meal.isPresent()) {
            return MealMapper.toDTO(meal.get());
        }

        throw new MealNotFoundException("Meal with id: " + id + " does not exist.");
    }

    public MealListDTO getAll() {

        List<Meal> meals = mealRepository.findAll();

        List<List<Ingredient>> ingredientsMeals = meals.stream().map(Meal::getIngredients).collect(Collectors.toList());
        List<List<IngredientDTO>> ingredientMealsDtos = new ArrayList<>();

        for (List<Ingredient> ingredientList :
                ingredientsMeals) {
            ingredientMealsDtos.add(ingredientList.stream().map(IngredientMapper::toDto).collect(Collectors.toList()));
        }

        MealListDTO mealListDTO = new MealListDTO();

        if (meals.isEmpty()) {
            mealListDTO.setMeals(Collections.emptyList());
            return mealListDTO;
        }

        List<MealDTO> allMeals = meals.stream().map(MealMapper::toDTO).collect(Collectors.toList());

        for (int i = 0; i < allMeals.size(); i++) {
            allMeals.get(i).setIngredients(ingredientMealsDtos.get(i));
        }

        mealListDTO.setMeals(allMeals);

        return mealListDTO;
    }

    public MealCaloriesDTO calculateCaloriesSum(Long id) {
        CalculateCaloriesTransaction calculateCaloriesTransaction = new CalculateCaloriesTransaction(mealRepository);
        return calculateCaloriesTransaction.execute(id);
    }
}
