package uep.diet.manager.meal;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uep.diet.manager.TestIngredient;
import uep.diet.manager.ingredient.domain.data.Ingredient;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.ingredient.dto.IngredientDTO;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.service.MealService;
import uep.diet.manager.meal.domain.exception.UpdateIngredientsException;
import uep.diet.manager.meal.dto.QuantityDTO;
import uep.diet.manager.meal.dto.UpdateIngredientsDTO;
import uep.diet.manager.meal.quantities.TestQuantities;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * @date 30.05.2021
 */
class UpdateMealIngredientsTest {

    private MealService subject;

    @Mock
    MealRepository mealRepository;
    @Mock
    IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subject = new MealService(mealRepository, ingredientRepository);
    }

    @Test
    void shouldReturnEmpty_whenEmptyIngredientsPassed() {
        // given
        UpdateIngredientsDTO body = new UpdateIngredientsDTO();
        body.setIngredientDTOList(new ArrayList<>());
        body.setQuantityListDTO(new ArrayList<>());
        when(ingredientRepository.save(any())).thenReturn(new Ingredient());
        when(mealRepository.findById(isA(Long.class))).thenReturn(Optional.of(TestMeal.basic()));
        when(ingredientRepository.findByMeal(any())).thenReturn(Optional.of(Arrays.asList(TestIngredient.basicWithId(1L))));

        // when
        UpdateIngredientsDTO actual = subject.updateMealIngredients(body, 1L);
        // then
        assertThat(actual.getIngredientDTOList()).isNotNull();
        assertThat(actual.getIngredientDTOList()).isEmpty();
    }

    @Test
    void shouldReturnUpdated_whenProperIngredientsPassed() {
        // given
        List<IngredientDTO> ingredients = Arrays.asList(
                TestIngredient.basicWithNameDTO("Test 1"),
                TestIngredient.basicWithNameDTO("Test 2"));
        TestQuantities.defaultQuantitiesDTOList(Arrays.asList(1L, 2L));

        UpdateIngredientsDTO body = new UpdateIngredientsDTO();
        body.setIngredientDTOList(ingredients);
        body.setQuantityListDTO(TestQuantities.defaultQuantitiesDTOList(Arrays.asList(1L, 2L)));

        when(ingredientRepository.save(isA(Ingredient.class))).thenReturn(TestIngredient.basicWithId(1L), TestIngredient.basicWithId(2L));
        when(mealRepository.save(isA(Meal.class))).thenReturn(TestMeal.withQuantities(10, 24));
        when(mealRepository.findById(isA(Long.class))).thenReturn(Optional.of(TestMeal.basic()));
        when(ingredientRepository.findByMeal(any())).thenReturn(Optional.of(Arrays.asList(TestIngredient.basicWithId(1L))));

        // when
        UpdateIngredientsDTO actual = subject.updateMealIngredients(body, 1L);
        // then
        List<IngredientDTO> expected = Arrays.asList(TestIngredient.basicWithIdDTO(1L), TestIngredient.basicWithIdDTO(2L));
        assertThat(actual.getIngredientDTOList())
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnUpdated_quantitesAdjusted() {
        // given
        List<IngredientDTO> passedIngredients = Arrays.asList(
                TestIngredient.basicWithNameDTO("Test 1"),
                TestIngredient.basicWithNameDTO("Test 2"));
        List<QuantityDTO> passedQuantities = TestQuantities.quantitesDTOForMeal(TestMeal.basic(), 10, 24);

        UpdateIngredientsDTO body = new UpdateIngredientsDTO();
        body.setIngredientDTOList(passedIngredients);
        body.setQuantityListDTO(passedQuantities);

        when(ingredientRepository.save(isA(Ingredient.class))).thenReturn(TestIngredient.basicWithId(1L), TestIngredient.basicWithId(2L));
        when(mealRepository.save(isA(Meal.class))).thenReturn(TestMeal.withQuantities(10, 24));
        when(mealRepository.findById(isA(Long.class))).thenReturn(Optional.of(TestMeal.withQuantities(0, 0)));
        when(ingredientRepository.findByMeal(any())).thenReturn(Optional.of(Arrays.asList(TestIngredient.basicWithId(1L))));
        // when
        UpdateIngredientsDTO actual = subject.updateMealIngredients(body, 1L);

        // then
        List<IngredientDTO> expectedIngredients = Arrays.asList(
                TestIngredient.basicWithIdDTO(1L),
                TestIngredient.basicWithIdDTO(2L));
        List<QuantityDTO> expectedQuantities = Arrays.asList(
                QuantityDTO.of(1L, 10, 1L),
                QuantityDTO.of(2L, 24, 2L));

        assertThat(actual.getQuantityListDTO())
                .isEqualTo(expectedQuantities);
        assertThat(actual.getIngredientDTOList())
                .isEqualTo(expectedIngredients);
    }

    @Test
    void shouldThrowWhenPassedQuantitesDoesNotMatchIngredients() {
        //given
        List<IngredientDTO> passedIngredients = Arrays.asList(
                TestIngredient.basicWithNameDTO("Test 1"),
                TestIngredient.basicWithNameDTO("Test 2"),
                TestIngredient.basicWithNameDTO("Test 3"));

        List<QuantityDTO> newQuantities = Collections.singletonList(QuantityDTO.of(1L, 10, 1L));

        UpdateIngredientsDTO body = new UpdateIngredientsDTO();
        body.setIngredientDTOList(passedIngredients);
        body.setQuantityListDTO(newQuantities);
        // when
        AbstractThrowableAssert<?, ? extends Throwable> actual = assertThatThrownBy(() -> subject.updateMealIngredients(body, 1L));
        // then
        actual.isInstanceOf(UpdateIngredientsException.class).hasMessage("Quantities should have same size as ingredients!");
    }
}
