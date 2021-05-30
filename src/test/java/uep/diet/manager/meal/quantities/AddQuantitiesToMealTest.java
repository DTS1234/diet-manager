package uep.diet.manager.meal.quantities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uep.diet.manager.ingredient.domain.exception.IngredientNotFoundException;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.meal.TestMeal;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.data.Quantity;
import uep.diet.manager.meal.domain.exception.MealNotFoundException;
import uep.diet.manager.meal.domain.exception.QuantityException;
import uep.diet.manager.meal.domain.service.MealService;
import uep.diet.manager.meal.dto.MealDTO;
import uep.diet.manager.meal.dto.QuantityDTO;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @date 27.05.2021
 */
class AddQuantitiesToMealTest {

    private MealService subject;

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private MealRepository mealRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subject = new MealService(mealRepository, ingredientRepository);
    }

    @Test
    void eachIngredientShouldHaveQuantityTwoIngredients() {
        // given
        Meal testMeal = TestMeal.basic();
        testMeal.getIngredients().get(0).setIngredientId(1L);
        testMeal.getIngredients().get(1).setIngredientId(2L);
        when(mealRepository.findById(isA(Long.class))).thenReturn(Optional.of(testMeal));
        // when
        MealDTO actual = subject.changeQuantityForIngredientInMeal(0, anyLong(), 1L);
        // then
        assertThat((long) actual.getQuantities().size())
                .isEqualTo(actual.getIngredients().size());
        assertThat(actual.getQuantities().stream().map(QuantityDTO::getQuantity).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList(0, 0));
    }


    @Test
    void ingredientsQuantityShouldChange() {
        // given
        Meal testMeal = TestMeal.basic();
        testMeal.getIngredients().get(0).setIngredientId(1L);
        testMeal.getIngredients().get(1).setIngredientId(2L);

        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));
        // when
        MealDTO actual = subject.changeQuantityForIngredientInMeal(10, 1L, 1L);
        // then
        assertThat((long) actual.getQuantities().size())
                .isEqualTo(actual.getIngredients().size());
        assertThat(actual.getQuantities().stream().map(QuantityDTO::getQuantity).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList(10, 0));
    }

    @Test
    void ingredientsQuantityShouldChange2() {
        // given
        Meal testMeal = TestMeal.basic();
        testMeal.getIngredients().get(0).setIngredientId(1L);
        testMeal.getIngredients().get(1).setIngredientId(2L);

        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));
        // when
        MealDTO actual = subject.changeQuantityForIngredientInMeal(20, 1L, 2L);
        // then
        assertThat((long) actual.getQuantities().size())
                .isEqualTo(actual.getIngredients().size());
        assertThat(actual.getQuantities().stream().map(QuantityDTO::getQuantity).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList(0, 20));
    }

    @Test
    void sameSizeIngredientsAndQuantities_WhenFoundQuantitiesAreGreater() {
        // given
        Meal testMeal = TestMeal.basic();
        testMeal.getIngredients().get(0).setIngredientId(1L);
        testMeal.getIngredients().get(1).setIngredientId(2L);
        testMeal.setQuantities(Arrays.asList(
                Quantity.of(100, testMeal),
                Quantity.of(19, testMeal),
                Quantity.of(50, testMeal)));

        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));
        // when
        MealDTO actual = subject.changeQuantityForIngredientInMeal(20, 1L, 2L);
        // then
        assertThat((long) actual.getQuantities().size())
                .isEqualTo(actual.getIngredients().size());
        assertThat(actual.getQuantities().stream().map(QuantityDTO::getQuantity).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList(100, 20));
    }

    @Test
    void sameSizeIngredientsAndQuantities_ChangingMultipleValues() {
        // given
        Meal testMeal = TestMeal.basicWith6Ingredients();
        testMeal.getIngredients().get(0).setIngredientId(1L);
        testMeal.getIngredients().get(1).setIngredientId(2L);
        testMeal.getIngredients().get(2).setIngredientId(3L);
        testMeal.getIngredients().get(3).setIngredientId(4L);
        testMeal.getIngredients().get(4).setIngredientId(5L);
        testMeal.getIngredients().get(5).setIngredientId(6L);

        testMeal.setQuantities(Arrays.asList(
                Quantity.of(100, testMeal),
                Quantity.of(19, testMeal),
                Quantity.of(5, testMeal),
                Quantity.of(25, testMeal),
                Quantity.of(33, testMeal),
                Quantity.of(150, testMeal)));

        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));
        // when
        subject.changeQuantityForIngredientInMeal(20, 1L, 2L);
        subject.changeQuantityForIngredientInMeal(29, 1L, 4L);
        MealDTO actual = subject.changeQuantityForIngredientInMeal(11, 1L, 6L);
        // then
        assertThat((long) actual.getQuantities().size())
                .isEqualTo(actual.getIngredients().size());
        assertThat(actual.getQuantities().stream().map(QuantityDTO::getQuantity).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList(100, 20, 5, 29, 33, 11));
    }

    @Test
    void addQuantitiesIfEmpty()
    {
        Meal testMeal = TestMeal.basicWith6Ingredients();
        testMeal.getIngredients().get(0).setIngredientId(1L);
        testMeal.getIngredients().get(1).setIngredientId(2L);
        testMeal.getIngredients().get(2).setIngredientId(3L);
        testMeal.getIngredients().get(3).setIngredientId(4L);
        testMeal.getIngredients().get(4).setIngredientId(5L);
        testMeal.getIngredients().get(5).setIngredientId(6L);

        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));

        // when
        subject.changeQuantityForIngredientInMeal(20, 1L, 2L);
        subject.changeQuantityForIngredientInMeal(29, 1L, 4L);
        MealDTO actual = subject.changeQuantityForIngredientInMeal(11, 1L, 6L);
        // then
        assertThat((long) actual.getQuantities().size())
                .isEqualTo(actual.getIngredients().size());
        assertThat(actual.getQuantities().stream().map(QuantityDTO::getQuantity).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList(0, 20, 0, 29, 0, 11));

    }

    @Test
    void ifQuantityIsNegativeThrow() {
        assertThatThrownBy(() -> {
            subject.changeQuantityForIngredientInMeal(-1, 1L, 2L);
        }).isInstanceOf(QuantityException.class).hasMessage("Quantity cannot be negative!");
    }

    @Test
    void ifNoSuchIngredientInMealThrow() {
        //given
        Meal testMeal = TestMeal.basic();
        testMeal.getIngredients().get(0).setIngredientId(1L);
        testMeal.getIngredients().get(1).setIngredientId(2L);

        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));
        //when
        assertThatThrownBy(() -> {
            subject.changeQuantityForIngredientInMeal(21, 1L, 1001L);
        }).isInstanceOf(IngredientNotFoundException.class);
    }

    @Test
    void ifNoSuchMealThrow() {
        //given
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> {
            subject.changeQuantityForIngredientInMeal(21, 1L, 1001L);
        }).isInstanceOf(MealNotFoundException.class);
    }

}
