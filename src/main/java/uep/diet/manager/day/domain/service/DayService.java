package uep.diet.manager.day.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uep.diet.manager.day.domain.data.DayRepository;
import uep.diet.manager.day.dto.DayDTO;
import uep.diet.manager.meal.domain.data.MealRepository;
import uep.diet.manager.user.domain.data.UserRepository;

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
}
