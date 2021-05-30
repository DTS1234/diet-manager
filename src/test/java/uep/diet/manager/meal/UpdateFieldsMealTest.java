package uep.diet.manager.meal;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uep.diet.manager.ingredient.domain.data.IngredientRepository;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.service.MealService;
import uep.diet.manager.meal.domain.exception.UpdateMealFieldsException;
import uep.diet.manager.meal.dto.UpdateFieldsDTO;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * @date 30.05.2021
 */
class UpdateFieldsMealTest {

    MealService subject;
    @Mock
    IngredientRepository ingredientRepository;
    @Mock
    MealRepository mealRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subject = new MealService(mealRepository, ingredientRepository);
    }

    @Test
    void shouldUpdateFields_BodyPassed() {
        //given
        UpdateFieldsDTO updateFieldsDTO = new UpdateFieldsDTO("newName", "newImage", "ITALIAN");
        when(mealRepository.findById(anyLong())).thenReturn(Optional.of(TestMeal.basicWith("name", "image", "American")));
        when(mealRepository.save(any())).thenReturn(TestMeal.basicWith("newName", "newImage", "ITALIAN"));
        //when
        UpdateFieldsDTO actual = subject.updateMealFields(updateFieldsDTO, 1L);
        //then
        assertThat(actual).isEqualTo(new UpdateFieldsDTO("newName", "newImage", "ITALIAN"));
    }

    @Test
    void shouldThrowIfAnyEmptyOrNull() {
        //given
        UpdateFieldsDTO updateFieldsDTO = new UpdateFieldsDTO(null, "newImage", "ITALIAN");
        //when
        AbstractThrowableAssert<?, ? extends Throwable> actual = assertThatThrownBy(() ->
                subject.updateMealFields(updateFieldsDTO, 1L));
        //then
        actual.isInstanceOf(UpdateMealFieldsException.class);
        actual.hasMessage("All fields are required !");
    }

}
