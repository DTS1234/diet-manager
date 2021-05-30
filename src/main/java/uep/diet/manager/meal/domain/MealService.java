package uep.diet.manager.meal.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.domain.IngredientNotFoundException;
import uep.diet.manager.ingredient.domain.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.dto.*;

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
        CreateMealTransaction createMealTransaction = new CreateMealTransaction(mealDTO, mealRepository, ingredientRepository);
        Meal createdMeal = createMealTransaction.execute();

        UpdateMealTransaction updateMealTransaction = new UpdateMealTransaction();
        Meal savedMeal = updateMealTransaction.execute(MealMapper.toDTO(createdMeal).getId(), mealRepository, ingredientRepository, MealMapper.toDTO(createdMeal));

        return MealMapper.toDTO(savedMeal);
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

    public MealDTO updateImgLink(Long id, String imgLink) {
        Meal meal = mealRepository.findById(id).orElseThrow(MealNotFoundException::new);
        meal.setImgLink(imgLink);

        return MealMapper.toDTO(mealRepository.save(meal));
    }

    public MealDTO addIngredientToMeal(Long mealId, Long ingredientId) {

        Meal meal = mealRepository.findById(mealId).orElseThrow(MealNotFoundException::new);
        List<Ingredient> mealIngredients = meal.getIngredients();

        Ingredient ingredientFound = ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);
        if (!mealIngredients.contains(ingredientFound)) {

            List<Meal> meals = ingredientFound.getMeal();
            meals.add(meal);
            ingredientRepository.save(ingredientFound);

            mealIngredients.add(ingredientFound);
        }
        meal.setIngredients(mealIngredients);

        return MealMapper.toDTO(mealRepository.save(meal));
    }

    public MealDTO changeQuantityForIngredientInMeal(Integer newQuantity, Long mealId, Long ingredientId) {
        ChangeQuantityTransaction changeQuantityTransaction = new ChangeQuantityTransaction(mealRepository);
        return changeQuantityTransaction.execute(newQuantity, mealId, ingredientId);
    }

    public UpdateIngredientsDTO updateMealIngredients(UpdateIngredientsDTO body,
                                                      Long mealId) {
        UpdateMealIngredientTransaction updateMealIngredientTransaction =
                new UpdateMealIngredientTransaction(ingredientRepository, mealRepository, mealId, body);

        return updateMealIngredientTransaction.execute();
    }

    public UpdateFieldsDTO updateMealFields(UpdateFieldsDTO updateFieldsDTO, Long mealId) {

        String mealType = updateFieldsDTO.getMealType();
        String imgLink = updateFieldsDTO.getImgLink();
        String name = updateFieldsDTO.getName();

        if (name == null || mealType == null || imgLink == null){
            throw new UpdateMealFieldsException("All fields are required !");
        }

        Meal mealFound = mealRepository.findById(mealId).orElseThrow(MealNotFoundException::new);

        mealFound.setMealType(mealType);
        mealFound.setImgLink(imgLink);
        mealFound.setName(name);

        Meal mealSaved = mealRepository.save(mealFound);

        return new UpdateFieldsDTO(mealSaved.getName(), mealSaved.getImgLink(), mealSaved.getMealType());
    }
}
