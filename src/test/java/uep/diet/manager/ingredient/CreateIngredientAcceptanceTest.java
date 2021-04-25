package uep.diet.manager.ingredient;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import uep.diet.manager.TestIngredient;
import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.domain.IngredientRepository;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author akazmierczak
 * @date 04.04.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Slf4j
class CreateIngredientAcceptanceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private IngredientRepository ingredientRepository;

    private Ingredient testIngredient;

    @BeforeEach
    void setUp() {
        ingredientRepository.deleteAll();
        testIngredient = TestIngredient.basicWithName("Test 1");
    }

    @Test
    void addIngredientShouldCreateItAndReturnCreated() {
        given()
                .port(port)
                .contentType("application/json")
                .body(testIngredient)
                .when()
                .post("ingredient/create")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo(testIngredient.getName()))
                .body("caloriesPer100g", equalTo(testIngredient.getCaloriesPer100g()))
                .body("fat", equalTo(testIngredient.getFat()))
                .body("carbohydrates", equalTo(testIngredient.getCarbohydrates()))
                .body("protein", equalTo(testIngredient.getProtein()));

        Ingredient ingredientFound = getIngredientFromDb();
        IngredientAssertions.assertThat(ingredientFound)
                .hasCaloriesEqualTo(testIngredient.getCaloriesPer100g())
                .hasCarbsEqualTo(testIngredient.getCarbohydrates())
                .hasFatEqualTo(testIngredient.getFat())
                .hasName(testIngredient.getName())
                .hasProteinEqualTo(testIngredient.getProtein());
    }

    private Ingredient getIngredientFromDb() {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findByName("Test 1");
        return optionalIngredient.orElseThrow(RuntimeException::new);
    }

    @AfterEach
    void clear() {
        ingredientRepository.deleteAll();
    }


}
