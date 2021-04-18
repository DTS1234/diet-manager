package uep.diet.manager.ingredient.domain;

import lombok.extern.slf4j.Slf4j;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;

/**
 * @author akazmierczak
 * @date 05.04.2021
 */
@Slf4j
class CreateIngredientTransaction {

    public Ingredient execute(IngredientRepository ingredientRepository, IngredientDTO ingredientDTO){

        if (ingredientDTO.getName().trim().equals(""))
        {
            throw new IngredientWrongParametersException("Name can't be empty.");
        }

        Ingredient ingredientToBeCreated = IngredientMapper.toEntity(ingredientDTO);

        return ingredientRepository.save(ingredientToBeCreated);
    }

}
