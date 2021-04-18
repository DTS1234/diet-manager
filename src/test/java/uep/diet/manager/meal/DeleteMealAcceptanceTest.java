package uep.diet.manager.meal;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.domain.MealRepository;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author akazmierczak
 * @date 29.03.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Slf4j
class DeleteMealAcceptanceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MealRepository mealRepository;

    @Test
    @Sql("/delete_meal_case.sql")
    void shouldDeleteExistingMealById() {
        given()
            .port(port)
            .contentType("application/json")
        .when()
            .delete("meal/1/delete")
        .then()
            .statusCode(200);

        assertFalse(mealRepository.existsById(1L));
        assertTrue(mealRepository.existsById(2L));
    }

}
