package uep.diet.manager.ingredient.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import uep.diet.manager.meal.domain.Meal;

import java.util.List;
import java.util.Optional;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String name);
    Optional<List<Ingredient>> findByMeal(Meal meal);

}
