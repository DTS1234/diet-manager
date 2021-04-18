package uep.diet.manager.meal.domain;

import lombok.Getter;
import lombok.Setter;
import uep.diet.manager.ingredient.domain.Ingredient;

import javax.persistence.*;
import java.util.List;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
@Entity
@Getter
@Setter
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mealId;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "meal")
    private List<Ingredient> ingredients;
}
