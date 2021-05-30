package uep.diet.manager.meal;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import uep.diet.manager.TestIngredient;
import uep.diet.manager.ingredient.domain.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.domain.MealRepository;
import uep.diet.manager.meal.dto.MealDTO;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author akazmierczak
 * @date 29.03.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Slf4j
class UpdateMealAcceptanceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    private MealDTO anyValidMeal;
    private MealDTO updatedValidMeal;
    private IngredientDTO updatedIngredient;

    @BeforeEach
    void setUp() {
        anyValidMeal = TestMeal.basicDTO();
        updatedIngredient = TestIngredient.basicWithIdDTO(3L);
        updatedIngredient.setName("Updated Ingredient");
        updatedValidMeal = anyValidMeal;
        updatedValidMeal.setName("updated");
        updatedValidMeal.setIngredients(Collections.singletonList(updatedIngredient));
    }

    @Test
    @Sql("/update_meal_case.sql")
    void shouldUpdateMeal() {
        Meal testMeal = TestMeal.basic();

        given()
            .port(port)
            .contentType("application/json")
            .body(anyValidMeal)
            .auth().preemptive().basic("adminUser", "pass123")

        .when()
            .patch("meal/1/update")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", equalTo("updated"))
            .body("ingredients.name", hasItem("Updated Ingredient"))
            .body("ingredients.caloriesPer100g", hasItems(updatedIngredient.getCaloriesPer100g()))
            .body("ingredients.fat", hasItem(updatedIngredient.getFat()))
            .body("ingredients.carbohydrates", hasItem(updatedIngredient.getCarbohydrates()))
            .body("ingredients.protein", hasItem(updatedIngredient.getProtein()));

        Meal actual = mealRepository.findById(1L).get();

        MealAssertions.assertThat(actual)
                    .hasName("updated")
                    .hasMealId(1L)
                    .hasIngredientWithName("Updated Ingredient", 0);
    }

}
