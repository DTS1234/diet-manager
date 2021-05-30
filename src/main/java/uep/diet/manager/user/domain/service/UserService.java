package uep.diet.manager.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uep.diet.manager.user.domain.data.User;
import uep.diet.manager.user.domain.exception.UserNotFoundException;
import uep.diet.manager.user.domain.data.UserRepository;
import uep.diet.manager.user.dto.*;

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

    public UserDTO updateDayLimit(Long userId, Integer newLimit)
    {
        ChangeKcalLimitTransaction changeKcalLimitTransaction = new ChangeKcalLimitTransaction(userRepository);
        return changeKcalLimitTransaction.execute(userId, newLimit);
    }

}
