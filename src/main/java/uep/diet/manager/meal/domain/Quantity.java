package uep.diet.manager.meal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * @author akazmierczak
 * @date 03.05.2021
 */
@Entity
@Data
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quantityId;
    private Integer grams;

    @ManyToOne
    private Meal meal;

    public static Quantity of(Integer grams, Meal meal){
        Quantity quantity = new Quantity();
        quantity.setGrams(grams);
        quantity.setMeal(meal);
        return quantity;
    }
}