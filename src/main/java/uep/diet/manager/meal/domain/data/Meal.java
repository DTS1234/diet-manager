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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "meal")
    private List<Ingredient> ingredients;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "meals")
    private Set<Day> days;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meal")
    private List<Quantity> quantities;

}
