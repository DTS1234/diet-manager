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
import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.domain.MealRepository;
import uep.diet.manager.meal.dto.MealDTO;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Slf4j
class CreateMealAcceptanceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MealRepository mealRepository;
    private MealDTO anyValidMeal;

    @BeforeEach
    void setUp() {
        anyValidMeal = TestMeal.basicDTO();
    }

    @Test
    @Sql("/create_meal_case.sql")
    void addMealRequestShouldReturnSuccessAndAddMeal() {
        Ingredient testIngredient1 = TestIngredient.basicWithName("Test 1");
        Ingredient testIngredient2 = TestIngredient.basicWithName("Test 2");

        given()
                .port(port)
                .contentType("application/json")
                .auth().preemptive().basic("adminUser", "pass123")
                .body(anyValidMeal)
                .when()
                .post("meal/create")
                .then()
            .statusCode(201)
                .body("id", is(greaterThan(0)))
                .body("name", equalTo("Pancakes"))
                .body("ingredients.id", hasItems(is(greaterThan(0)), is(greaterThan(0))))
                .body("ingredients.name", hasItems("Test 1", "Test 2"))
                .body("ingredients.caloriesPer100g", hasItems(testIngredient1.getCaloriesPer100g(), testIngredient2.getCaloriesPer100g()))
                .body("ingredients.fat", hasItems(testIngredient1.getFat(), testIngredient2.getFat()))
                .body("ingredients.carbohydrates", hasItems(testIngredient1.getCarbohydrates(), testIngredient2.getCarbohydrates()))
                .body("ingredients.protein", hasItems(testIngredient1.getProtein(), testIngredient2.getProtein()));

        Meal mealFound = getMealFromDatabase();
        MealAssertions.assertThat(mealFound)
                .hasName(anyValidMeal.getName())
                .hasIngredientWithName("Test 1", 0)
                .hasIngredientWithName("Test 2", 1);
    }

    private Meal getMealFromDatabase() {
        Optional<Meal> optionalMeal = mealRepository.findByName("Pancakes");
        return optionalMeal.orElseThrow(RuntimeException::new);
    }

}
