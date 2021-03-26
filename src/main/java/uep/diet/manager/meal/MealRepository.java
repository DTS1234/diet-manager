package uep.diet.manager.meal;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
public interface MealRepository extends JpaRepository<Meal, Long> {
}
