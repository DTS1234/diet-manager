package uep.diet.manager.meal;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author akazmierczak
 * @date 26.03.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class MealAcceptanceTest {

    @LocalServerPort
    private int port;
    private Meal anyValidMeal;
    private MockMvc mockMvc;
    @Autowired
    private MealRepository mealRepository;

    @BeforeEach
    void setUp() {
        anyValidMeal = TestMeal.basic();
    }

    @Test
    void addMealRequestShouldReturnSuccessAndAddMeal() {
        given()
                .port(port)
                .contentType("application/json")
                .body(anyValidMeal)
        .when()
                .post("meal/add")
        .then()
                .statusCode(201)
                .body("mealId", equalTo(1));

        Optional<Meal> optionalMeal = mealRepository.findById(1L);

        assertThat(optionalMeal.get()).isEqualTo(anyValidMeal);
    }


}
