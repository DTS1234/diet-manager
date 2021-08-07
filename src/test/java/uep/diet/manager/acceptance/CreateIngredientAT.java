package uep.diet.manager.acceptance;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import uep.diet.manager.ingredient.TestIngredient;
import uep.diet.manager.ingredient.IngredientAssertions;
import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.ingredient.dto.IngredientMapper;

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
class CreateIngredientAT {

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
        IngredientDTO actual = given()
                .port(port)
                .contentType("application/json")
                .auth().preemptive().basic("adminUser", "pass123")
                .body(testIngredient)
                .when()
                .post("ingredient/create")
                .then()
                .statusCode(201)
                .extract().as(IngredientDTO.class);

        Ingredient actualIngredientEntity = IngredientMapper.toEntity(actual);
        IngredientAssertions.assertThat(actualIngredientEntity)
                .hasCaloriesEqualTo(testIngredient.getCaloriesPer100g())
                .hasCarbsEqualTo(testIngredient.getCarbohydrates())
                .hasFatEqualTo(testIngredient.getFat())
                .hasName(testIngredient.getName())
                .hasProteinEqualTo(testIngredient.getProtein());

        Ingredient actualIngredientInDb = getIngredientFromDb();
        IngredientAssertions.assertThat(actualIngredientInDb)
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
