package uep.diet.manager.user.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import uep.diet.manager.day.domain.Day;
import uep.diet.manager.day.dto.DayDTO;
import uep.diet.manager.day.dto.DayMapper;
import uep.diet.manager.user.dto.UserDTO;
import uep.diet.manager.user.dto.UserDTOList;
import uep.diet.manager.user.dto.UserDaysDTO;
import uep.diet.manager.user.dto.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            return UserMapper.toDTO(userOptional.get());
        } else {
            throw new UserNotFoundException("User with username equal to " + username + " was not found.");
        }
    }

    public UserDTO getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return UserMapper.toDTO(userOptional.get());
        } else {
            throw new UserNotFoundException("User with id equal to " + id + " was not found.");
        }
    }

    public UserDTOList getAllUsers() {
        return new UserDTOList(userRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList()));
    }

    public UserDaysDTO getUserWithDays(Long id) {
        GetUserDaysTransaction getUserDays = new GetUserDaysTransaction(userRepository);
        return getUserDays.execute(id);
    }
}
