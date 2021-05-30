package uep.diet.manager.day.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;
import uep.diet.manager.day.domain.data.Day;

/**
 * @author akazmierczak
 * @date 18.04.2021
 */
public interface DayRepository extends JpaRepository<Day, Long> {}
