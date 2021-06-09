package uep.diet.manager.day.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uep.diet.manager.day.domain.data.Day;
import uep.diet.manager.day.domain.data.DayRepository;
import uep.diet.manager.day.domain.exception.DayNotFoundException;
import uep.diet.manager.day.dto.DayDTO;
import uep.diet.manager.day.dto.DayMapper;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.meal.domain.exception.MealNotFoundException;
import uep.diet.manager.user.domain.data.User;
import uep.diet.manager.user.domain.data.UserRepository;
import uep.diet.manager.user.domain.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 18.04.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DayService {

    private final DayRepository dayRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;

    /** Adds a day record to user for a particular date, only one day can be registered to one date. */
    public DayDTO createDayForUser(Long userId, DayDTO dayDTO) {
        AddDayToUserTransaction addDayToUserTransaction = new AddDayToUserTransaction(userRepository, dayRepository);
        return addDayToUserTransaction.execute(userId, dayDTO);
    }

    public DayDTO addMealToUsersDay(Long userId, Long dayId, Long mealId) {
        AddMealToUsersDayTransaction addMealToUsersDayTransaction = new AddMealToUsersDayTransaction(userRepository, dayRepository, mealRepository);
        return addMealToUsersDayTransaction.execute(userId, dayId, mealId);
    }

    public DayDTO removeMealFromUsersDay(Long userId, Long dayId, Long mealId) {
        RemoveMealFromUsersDayTransaction removeMealFromUsersDayTransaction = new RemoveMealFromUsersDayTransaction(userRepository, dayRepository);
        return removeMealFromUsersDayTransaction.execute(userId, dayId, mealId);
    }

    public DayDTO addMealToUsersDay(Long userId, LocalDate date, Long mealId){

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Day dayFound = user.getDays().stream().filter(day -> day.getDate().equals(date)).findFirst().orElseThrow(() -> new DayNotFoundException("Day does not exist."));

        List<Meal> meals = dayFound.getMeals();
        Meal meal = mealRepository.findById(mealId).orElseThrow(MealNotFoundException::new);

        if (!meals.contains(meal))
        {
            meals.add(meal);
        }

        dayFound.setMeals(meals);

        Day save = dayRepository.save(dayFound);

        Set<Day> days = meal.getDays();
        days.add(dayFound);
        meal.setDays(days);
        mealRepository.save(meal);

        return DayMapper.toDTO(save);
    }

}
