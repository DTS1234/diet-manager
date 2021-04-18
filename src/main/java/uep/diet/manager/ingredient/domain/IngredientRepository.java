package uep.diet.manager.ingredient.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
