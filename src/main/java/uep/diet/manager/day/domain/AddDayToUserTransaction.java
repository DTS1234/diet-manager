package uep.diet.manager.day.domain;

import uep.diet.manager.day.dto.DayDTO;
import uep.diet.manager.day.dto.DayMapper;
import uep.diet.manager.user.domain.User;
import uep.diet.manager.user.domain.UserNotFoundException;
import uep.diet.manager.user.domain.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
class AddDayToUserTransaction {

    private final UserRepository userRepository;
    private final DayRepository dayRepository;

    public AddDayToUserTransaction( UserRepository userRepository, DayRepository dayRepository) {
        this.userRepository = userRepository;
        this.dayRepository = dayRepository;
    }

    DayDTO execute(Long userId, DayDTO dayDTO) {

        Optional<User> optionalUser = userRepository.findById(userId);
        User userFound;

        if (optionalUser.isPresent())
        {
            userFound = optionalUser.get();
            List<Day> userDays = userFound.getDays();
            List<DayDTO> userDaysDTOS = userDays.stream().map(DayMapper::toDTO).collect(Collectors.toList());

            if (dayDTO.getId() != null && dayRepository.existsById(dayDTO.getId()))
            {
                throw new DayAddException("Day with id " + dayDTO.getId() + " already exists");
            }

            if (!userDaysDTOS.stream().map(DayDTO::getDate).collect(Collectors.toList()).contains(dayDTO.getDate())) {
                Day dayToBeSaved = DayMapper.toEntity(dayDTO);
                Day savedDay = dayRepository.save(dayToBeSaved);

                userDays.add(savedDay);
                userFound.setDays(userDays);
                userRepository.save(userFound);

                dayDTO.setId(savedDay.getDayId());
                dayDTO.setMeals(Collections.EMPTY_LIST);

                return dayDTO;
            }else {
                throw new DayAddException("Day with date " + dayDTO.getDate() + " already exists for that user. (" + userFound.getUsername() + ")");
            }

        }else {
            throw new UserNotFoundException(String.format("User with id %s not found.", userId));
        }

    }

}
