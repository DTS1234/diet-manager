package uep.diet.manager.meal.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @date 29.05.2021
 */
public interface QuantityRepository extends JpaRepository<Quantity, Long> {
}