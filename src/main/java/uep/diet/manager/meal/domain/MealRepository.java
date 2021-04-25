package uep.diet.manager.meal.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByName(String name);
}
