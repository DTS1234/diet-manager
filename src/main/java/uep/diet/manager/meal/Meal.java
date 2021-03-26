package uep.diet.manager.meal;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
@Entity
@Data
public class Meal {

    @Id
    private Long mealId;

}
