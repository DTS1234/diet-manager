package uep.diet.manager.meal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
@RestController
@RequestMapping(value = "meal")
@RequiredArgsConstructor
public class MealController {

    private final MealRepository mealRepository;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Meal add(@RequestBody Meal meal){
        mealRepository.save(meal);
        return meal;
    }

}
