package uep.diet.manager.day.domain.service;

import uep.diet.manager.day.domain.data.Day;
import uep.diet.manager.day.domain.data.DayRepository;
import uep.diet.manager.day.domain.exception.DayAddException;
import uep.diet.manager.day.dto.DayDTO;
import uep.diet.manager.day.dto.DayMapper;
import uep.diet.manager.user.domain.data.User;
import uep.diet.manager.user.domain.data.UserRepository;
import uep.diet.manager.user.domain.exception.UserNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
class AddDayToUserTransaction {

    private final UserRepository userRepository;
    private final DayRepository dayRepository;

    public AddDayToUserTransaction(UserRepository userRepository, DayRepository dayRepository) {
        this.userRepository = userRepository;
        this.dayRepository = dayRepository;
    }

    DayDTO execute(Long userId, DayDTO dayDTO) {

        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found.", userId)));

        List<Day> userDays = userFound.getDays();
        List<DayDTO> userDaysDTOS = userDays.stream().map(DayMapper::toDTO).collect(Collectors.toList());

        if (dayDTO.getId() != null && dayRepository.existsById(dayDTO.getId())) {
            throw new DayAddException("Day with id " + dayDTO.getId() + " already exists");
        }

        if (!userDaysDTOS.stream().map(DayDTO::getDate).collect(Collectors.toList()).contains(dayDTO.getDate())) {
            Day dayToBeSaved = DayMapper.toEntity(dayDTO);
            Day savedDay = dayRepository.save(dayToBeSaved);

            userDays.add(savedDay);
            userFound.setDays(userDays);
            userRepository.save(userFound);

            dayDTO.setId(savedDay.getDayId());
            dayDTO.setMeals(Collections.emptyList());

            return dayDTO;
        } else {
            throw new DayAddException("Day with date " + dayDTO.getDate() + " already exists for that user. (" + userFound.getUsername() + ")");
        }

    }

}
