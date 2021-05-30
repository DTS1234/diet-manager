package uep.diet.manager.meal;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uep.diet.manager.meal.domain.MealService;
import uep.diet.manager.meal.dto.*;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
@RestController
@RequestMapping("meal")
@RequiredArgsConstructor
@Slf4j
public class MealController {

    private final MealService mealService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MealDTO create(@RequestBody MealDTO mealDTO) {
        return mealService.createMeal(mealDTO);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return "Meal deleted successfully";
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MealDTO update(@PathVariable Long id, @RequestBody MealDTO newMealDTO) {
        return mealService.updateMeal(id, newMealDTO);
    }

    @GetMapping("/{id}")
    public MealDTO getById(@PathVariable Long id) {
        return mealService.getById(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Returns all meals available")
    public MealListDTO getAll() {
        return mealService.getAll();
    }

    @GetMapping("/{id}/calories")
    @Operation(description = "Returns sum of calories for meal.")
    public MealCaloriesDTO getCaloriesSum(@PathVariable Long id) {
        return mealService.calculateCaloriesSum(id);
    }

    @PutMapping("/{id}/changeImg/{imgLink}")
    public MealDTO updateImg(@PathVariable Long id, @PathVariable String imgLink) {
        return mealService.updateImgLink(id, imgLink);
    }

    @PostMapping("/{mealId}/addIngredient/{ingredientId}")
    public MealDTO addIngredientToMeal(@PathVariable Long mealId, @PathVariable Long ingredientId) {
        return mealService.addIngredientToMeal(mealId, ingredientId);
    }

    @PostMapping("/{mealId}/addQuantityToIngredient/{ingredientId}/newQuantity/{quantity}")
    public MealDTO addQuantityToIngredient(@PathVariable Long mealId,
                                           @PathVariable Long ingredientId,
                                           @PathVariable Integer quantity) {
        return mealService.changeQuantityForIngredientInMeal(quantity, mealId, ingredientId);
    }

    @PatchMapping("/{mealId}/updateIngredients/")
    public UpdateIngredientsDTO updateIngredients(@PathVariable Long mealId, @RequestBody UpdateIngredientsDTO body)
    {
        return mealService.updateMealIngredients(body, mealId);
    }

    @PatchMapping("/{mealId}/updateFields/")
    public UpdateFieldsDTO updateMealFields(@PathVariable Long mealId, @RequestBody UpdateFieldsDTO body)
    {
        return mealService.updateMealFields(body, mealId);
    }

}
