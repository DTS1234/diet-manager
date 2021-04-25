package uep.diet.manager.meal;

import groovy.util.logging.Slf4j;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import uep.diet.manager.meal.domain.MealRepository;

import static io.restassured.RestAssured.*;

/**
 * @author akazmierczak
 * @date 17.04.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
class GetAllMealsAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MealRepository mealRepository;

    @Test
    @Sql({"/get_all_meals.sql"})
    void getAllMealsTest() {

        get("meal/all").getBody().prettyPrint();

        given()
                .port(port)
                .contentType("application/json")
        .when()
                .get("meal/all")
        .then()
                .statusCode(200)
                .body("meals[0].name", Matchers.equalTo("Spaghetti"))
                .body("meals[1].name", Matchers.equalTo("Pizza"))
                .body("meals[2].name", Matchers.equalTo("Beef Steak"))
                .body("meals[3].name", Matchers.equalTo("Tiramisu"))
                .body("meals[4].name", Matchers.equalTo("Pancakes"))
                .body("meals[0].ingredients[0].name", Matchers.equalTo("name"))
                .body("meals[0].ingredients[0].caloriesPer100g", Matchers.equalTo(12))
                .body("meals[0].ingredients[0].fat", Matchers.equalTo(14))
                .body("meals[0].ingredients[0].protein", Matchers.equalTo(15))
                .body("meals[0].ingredients[0].carbohydrates", Matchers.equalTo(13));
    }
}
