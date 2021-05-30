package uep.diet.manager.day.domain.service;

import lombok.extern.slf4j.Slf4j;
import uep.diet.manager.day.domain.data.Day;
import uep.diet.manager.day.domain.data.DayRepoUtils;
import uep.diet.manager.day.domain.data.DayRepository;
import uep.diet.manager.day.domain.exception.RemoveMealFromUsersDayException;
import uep.diet.manager.day.dto.DayDTO;
import uep.diet.manager.day.dto.DayMapper;
import uep.diet.manager.meal.domain.data.Meal;
import uep.diet.manager.user.domain.data.UserRepoUtils;
import uep.diet.manager.user.domain.data.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 25.04.2021
 */
@Slf4j
class RemoveMealFromUsersDayTransaction {

    private final UserRepository userRepository;
    private final DayRepository dayRepository;

    public RemoveMealFromUsersDayTransaction(UserRepository userRepository, DayRepository dayRepository) {
        this.userRepository = userRepository;
        this.dayRepository = dayRepository;
    }

    DayDTO execute(Long userId, Long dayId, Long mealId) {
        UserRepoUtils userRepoUtils = new UserRepoUtils(userRepository);
        userRepoUtils.throwIfDoesntExists(userId);

        DayRepoUtils dayRepoUtils = new DayRepoUtils(dayRepository);
        Day dayFound = dayRepoUtils.findDayByIdOrThrow(dayId);

        if (userRepoUtils.dayAlreadyRegisteredForUser(userId, dayId)) {

            if (dayRepoUtils.mealAlreadyAssignedToDay(dayId, mealId)) {
                List<Meal> meals = dayFound.getMeals();
                Meal mealToBeDeleted = getMealToBeDeleted(mealId, meals);

                meals.remove(mealToBeDeleted);
                dayFound.setMeals(meals);
                Day dayUpdated = dayRepository.save(dayFound);

                log.info("Meal with id: " + mealId + " has  been removed from day with id " + dayId);

                return DayMapper.toDTO(dayUpdated);
            } else {
                throw new RemoveMealFromUsersDayException("Meal with id " + mealId + " is not assigned to that day so cannot be removed");
            }

        } else {
            throw new RemoveMealFromUsersDayException("Day with id " + dayId + " is not registered for that user.");
        }

    }

    private Meal getMealToBeDeleted(Long mealId, List<Meal> meals) {
        return meals
                .stream()
                .filter(meal -> meal.getMealId().equals(mealId))
                .collect(Collectors.toList()).get(0);
    }

}


