package uep.diet.manager.ingredient.domain;

import lombok.*;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.domain.Quantity;

import javax.persistence.*;
import java.util.List;

/**
 * @author akazmierczak
 * @date 27.03.2021
 */
@Entity
@Getter
@Setter
@ToString
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @ManyToMany
    @JoinTable(
            name = "ingredient_meal",
            joinColumns = @JoinColumn(name = "mealId"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId"))
    private List<Meal> meal;

    private int caloriesPer100g;
    private String name;
    private int fat;
    private int protein;
    private int carbohydrates;
}