package uep.diet.manager.ingredient;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uep.diet.manager.ingredient.domain.IngredientService;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientDTOList;

/**
 * @author akazmierczak
 * @date 04.04.2021
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientDTO create(@RequestBody IngredientDTO ingredientDTO) {
        return ingredientService.createIngredient(ingredientDTO);
    }

    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get ingredient by its id")
    public IngredientDTO getById(@PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public IngredientDTOList getAll() {
        return ingredientService.getAll();
    }

    @DeleteMapping(value = "{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable Long id) {
        ingredientService.deleteById(id);
        return "Deleted successfully";
    }

}
