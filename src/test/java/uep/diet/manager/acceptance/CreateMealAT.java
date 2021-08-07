package uep.diet.manager.acceptance;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import uep.diet.manager.ingredient.TestIngredient;
import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.meal.MealAssertions;
import uep.diet.manager.meal.TestMeal;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.MealMapper;

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
class CreateMealAT {

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

        MealDTO actual = given()
                .port(port)
                .contentType("application/json")
                .auth().preemptive().basic("adminUser", "pass123")
                .body(anyValidMeal)
                .when()
                .post("meal/create")
                .then()
                .statusCode(201)
                .extract().as(MealDTO.class);

        Meal actualEntity = MealMapper.toEntity(actual);

        MealAssertions.assertThat(actualEntity)
                .hasName(anyValidMeal.getName())
                .hasIngredientWithName("Test 1", 0)
                .hasIngredientWithName("Test 2", 1);

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
