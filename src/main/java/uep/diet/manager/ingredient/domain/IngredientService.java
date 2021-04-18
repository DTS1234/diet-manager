package uep.diet.manager.ingredient.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uep.diet.manager.ingredient.domain.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;
import uep.diet.manager.meal.dto.MealDTO;

import java.util.Optional;

/**
 * @author akazmierczak
 * @date 05.04.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;


    public IngredientDTO createIngredient(IngredientDTO ingredientDTO) {

        CreateIngredientTransaction createIngredientTransaction = new CreateIngredientTransaction();
        Ingredient savedIngredient = createIngredientTransaction.execute(ingredientRepository, ingredientDTO);

        return IngredientMapper.toDto(savedIngredient);
    }

    public IngredientDTO getById(Long id) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if (ingredient.isPresent())
        {
            return IngredientMapper.toDto(ingredient.get());
        }

        throw new IngredientNotFoundException("Ingredient with id " + id + " was not found.");

    }
}
