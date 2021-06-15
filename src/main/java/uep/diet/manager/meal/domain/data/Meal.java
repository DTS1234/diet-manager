package uep.diet.manager.meal.domain.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import uep.diet.manager.day.domain.data.Day;
import uep.diet.manager.ingredient.domain.data.Ingredient;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private String imgLink;
    private String mealType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "meal")
    private List<Ingredient> ingredients;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "meals")
    private Set<Day> days;

    public int getCalories() {
        return ingredients
                .stream()
                .mapToInt(value -> (value.getCaloriesPer100g() * value.getQuantityInGrams()) / 100).sum();
    }


}
