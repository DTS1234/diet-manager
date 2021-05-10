package uep.diet.manager.meal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author akazmierczak
 * @date 03.05.2021
 */
@Entity
@Data
public class Quantity {
    @Id
    @JsonIgnore
    private Long quantityId;
    private Integer grams;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "quantity_meal",
            joinColumns = @JoinColumn(name = "mealId"),
            inverseJoinColumns = @JoinColumn(name = "quantityId"))
    private List<Meal> meals;

}
