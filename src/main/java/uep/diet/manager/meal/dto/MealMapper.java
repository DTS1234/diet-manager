package uep.diet.manager.meal.dto;

import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.domain.data.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
public class MealMapper {

    private MealMapper() {
    }

    public static MealDTO toDTO(Meal meal) {

        String name = meal.getName();
        List<Ingredient> ingredientList = meal.getIngredients() == null ? new ArrayList<>() : meal.getIngredients();
        Long id = meal.getMealId();
        String imgLink = meal.getImgLink() == null ? "" : meal.getImgLink();
        String mealType = meal.getMealType() == null ? "" : meal.getMealType();

        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(id);
        mealDTO.setName(name);
        mealDTO.setImgLink(imgLink);
        mealDTO.setMealType(mealType);

        if (ingredientList != null && !ingredientList.isEmpty()) {
            // if ingredients exists set them up
            mealDTO.setIngredients(ingredientList
                    .stream()
                    .map(IngredientMapper::toDto)
                    .collect(Collectors.toList()));

        } else {
            mealDTO.setIngredients(new ArrayList<>());
        }

        return mealDTO;
    }

    public static Meal toEntity(MealDTO mealDTO) {
        String name = mealDTO.getName();
        List<IngredientDTO> ingredientList = mealDTO.getIngredients();
        Long id = mealDTO.getId();

        Meal meal = new Meal();
        meal.setMealId(id);
        meal.setName(name);
        meal.setIngredients(ingredientList
                .stream()
                .map(IngredientMapper::toEntity)
                .collect(Collectors.toList()));
        meal.setImgLink(mealDTO.getImgLink());
        meal.setMealType(mealDTO.getMealType());

        return meal;
    }


}
