package uep.diet.manager.ingredient.domain.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uep.diet.manager.meal.domain.data.Meal;

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

    @ManyToOne
    private Meal meal;

    private int caloriesPer100g;
    private String name;
    private int fat;
    private int protein;
    private int carbohydrates;
    private int quantityInGrams;

}
