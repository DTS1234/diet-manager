package uep.diet.manager.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uep.diet.manager.TestIngredient;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.service.MealService;
import uep.diet.manager.meal.domain.data.Quantity;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.MealMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * @date 28.05.2021
 */
class CreateMealTest {

    @Mock
    IngredientRepository ingredientRepository;
    @Mock
    MealRepository mealRepository;

    MealService subject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subject = new MealService(mealRepository, ingredientRepository);
    }

    @Test
    void createsMealFromMealDTOBodyCorrectly_WhenNoIngredientsPresent() {
        //given
        MealDTO mealDTO = new MealDTO();
        mealDTO.setImgLink("img link");
        mealDTO.setName("some-name");

        Meal testMeal = new Meal();
        testMeal.setMealId(1L);
        testMeal.setIngredients(null);
        testMeal.setQuantities(null);
        testMeal.setImgLink("img link");
        testMeal.setName("some-name");

        when(mealRepository.save(any())).thenReturn(testMeal);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));

        //when
        MealDTO actual = subject.createMeal(mealDTO);

        MealDTO expected = new MealDTO();
        expected.setIngredients(Collections.emptyList());
        expected.setQuantities(Collections.emptyList());
        expected.setName("some-name");
        expected.setImgLink("img link");
        expected.setMealType("");
        expected.setId(1L);

        //then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void createsMealFromMealDTOBodyCorrectly_WhenExistingIngredientsIdsInABody() {
        //given
        MealDTO mealDTO = new MealDTO();
        mealDTO.setImgLink("img link");
        mealDTO.setName("some-name");
        mealDTO.setIngredients(Arrays.asList(TestIngredient.onlyWithIdDTO(1L), TestIngredient.onlyWithIdDTO(2L)));

        Meal testMeal = new Meal();
        testMeal.setMealId(1L);
        testMeal.setIngredients(Arrays.asList(TestIngredient.basicWithId(1L), TestIngredient.basicWithId(2L)));
        testMeal.setQuantities(null);
        testMeal.setImgLink("img link");
        testMeal.setName("some-name");

        when(mealRepository.save(any())).thenReturn(testMeal);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));

        //when
        MealDTO actual = subject.createMeal(mealDTO);

        MealDTO expected = new MealDTO();
        expected.setQuantities(Arrays.asList(MealMapper.quantityToDTO(Quantity.of(0, testMeal)),
                MealMapper.quantityToDTO(Quantity.of(0, testMeal))));
        expected.setName("some-name");
        expected.setImgLink("img link");
        expected.setId(1L);

        //then
        assertThat(actual.getIngredients())
                .isEqualTo(Arrays.asList(TestIngredient.basicWithIdDTO(1L), TestIngredient.basicWithIdDTO(2L)));
    }

}
