package uep.diet.manager.day.domain;

import lombok.Data;
import uep.diet.manager.meal.domain.Meal;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author akazmierczak
 * @date 18.04.2021
 */
@Data
@Entity
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;
    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "day_meal",
            joinColumns = @JoinColumn(name = "dayId"),
            inverseJoinColumns = @JoinColumn(name = "mealId"))
    private List<Meal> meals;

}
