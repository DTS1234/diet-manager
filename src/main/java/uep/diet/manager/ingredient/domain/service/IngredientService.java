package uep.diet.manager.ingredient.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uep.diet.manager.ingredient.domain.exception.IngredientNotFoundException;
import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientDTOList;
import uep.diet.manager.ingredient.dto.IngredientMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (ingredient.isPresent()) {
            return IngredientMapper.toDto(ingredient.get());
        }

        throw new IngredientNotFoundException("Ingredient with id " + id + " was not found.");
    }

    public IngredientDTOList getAll() {

        IngredientDTOList ingredientDTOList = new IngredientDTOList();

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        List<IngredientDTO> targetList = new ArrayList<>();

        if (ingredientList == null) {
            ingredientDTOList.setIngredients(targetList);
            return ingredientDTOList;
        }

        ingredientDTOList.setIngredients(ingredientList.stream().map(IngredientMapper::toDto).collect(Collectors.toList()));
        return ingredientDTOList;
    }

    public void deleteById(Long id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        if (ingredientOptional.isPresent()) {
            ingredientRepository.deleteById(id);
        } else {
            throw new IngredientNotFoundException("No ingredient with id: " + id);
        }
    }

    public IngredientDTOList findByText(String text) {

        List<Ingredient> allIngredients = ingredientRepository.findAll();

        List<IngredientDTO> filtered = allIngredients.stream()
                .filter(element -> element.getName().toLowerCase().startsWith(text))
                .collect(Collectors.toList())
                    .stream()
                    .map(IngredientMapper::toDto)
                    .collect(Collectors.toList());

        IngredientDTOList ingredientDTOList = new IngredientDTOList();
        ingredientDTOList.setIngredients(filtered);
        return ingredientDTOList;

    }
}
