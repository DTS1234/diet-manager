package uep.diet.manager.day.domain;

import lombok.extern.slf4j.Slf4j;
import uep.diet.manager.day.dto.DayDTO;
import uep.diet.manager.day.dto.DayMapper;
import uep.diet.manager.meal.domain.Meal;
import uep.diet.manager.meal.domain.MealRepoUtils;
import uep.diet.manager.meal.domain.MealRepository;
import uep.diet.manager.user.domain.UserRepoUtils;
import uep.diet.manager.user.domain.UserRepository;

import java.util.Collections;
import java.util.List;

/**
 * @author akazmierczak
 * @date 23.04.2021
 */
@Slf4j
class AddMealToUsersDayTransaction {

    private final UserRepository userRepository;
    private final DayRepository dayRepository;
    private final MealRepository mealRepository;

    public AddMealToUsersDayTransaction(UserRepository userRepository,
                                        DayRepository dayRepository,
                                        MealRepository mealRepository) {
        this.userRepository = userRepository;
        this.dayRepository = dayRepository;
        this.mealRepository = mealRepository;
    }

    DayDTO execute(Long userId, Long dayId, Long mealId) {

        UserRepoUtils userRepoUtils = new UserRepoUtils(userRepository);
        userRepoUtils.throwIfDoesntExists(userId);

        DayRepoUtils dayRepoUtils = new DayRepoUtils(dayRepository);
        Day dayFound = dayRepoUtils.findDayByIdOrThrow(dayId);

        if (userRepoUtils.dayAlreadyRegisteredForUser(userId, dayId)) {

            MealRepoUtils mealRepoUtils = new MealRepoUtils(mealRepository);
            Meal mealFound = mealRepoUtils.findByMealByIdOrThrow(mealId);
            List<Meal> meals = dayFound.getMeals();

            if (meals != null) {
                meals.add(mealFound);
            } else {
                meals = Collections.singletonList(mealFound);
            }

            dayFound.setMeals(meals);
            Day daySaved = dayRepository.save(dayFound);

            log.info("meal added to day with id " + dayId + " of user with id " + userId);

            return DayMapper.toDTO(daySaved);

        } else {
            throw new DayAddException("This day does is not assigned to this user.");
        }

    }

}

