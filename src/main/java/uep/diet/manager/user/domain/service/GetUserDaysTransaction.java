package uep.diet.manager.user.domain.service;

import uep.diet.manager.day.domain.data.Day;
import uep.diet.manager.day.dto.DayDTO;
import uep.diet.manager.day.dto.DayMapper;
import uep.diet.manager.user.domain.data.User;
import uep.diet.manager.user.domain.data.UserRepository;
import uep.diet.manager.user.domain.exception.UserNotFoundException;
import uep.diet.manager.user.dto.UserDaysDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
class GetUserDaysTransaction {

    private final UserRepository userRepository;

    public GetUserDaysTransaction(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDaysDTO execute(Long id) {
        User userFound = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id equal to " + id + " was not found."));
        UserDaysDTO userDaysDTO = new UserDaysDTO();

        userDaysDTO.setUsername(userFound.getUsername());
        userDaysDTO.setId(userFound.getUserId());

        boolean userHaveSomeDaysAlready = userFound.getDays() != null && !userFound.getDays().isEmpty();

        if (userHaveSomeDaysAlready) {

            List<Day> days = userFound.getDays();
            List<DayDTO> dayDTOS = days.stream().map(DayMapper::toDTO).collect(Collectors.toList());
            userDaysDTO.setDays(dayDTOS);
            return userDaysDTO;
        }
        userDaysDTO.setDays(Collections.emptyList());
        return userDaysDTO;
    }
}
