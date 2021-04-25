package uep.diet.manager.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uep.diet.manager.day.domain.Day;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author akazmierczak
 * @date 25.04.2021
 */
@RequiredArgsConstructor
public class UserRepoUtils {

    private final UserRepository userRepository;

    public User findUserByIdOrThrow(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent())
        {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("There is no user with id " + userId);
        }
    }

    public boolean dayAlreadyRegisteredForUser(Long userId, Long dayId)
    {
        User userFound = findUserByIdOrThrow(userId);
        List<Long> dayIds = userFound.getDays().stream().map(Day::getDayId).collect(Collectors.toList());
        return dayIds.contains(dayId);
    }

    public void throwIfDoesntExists(Long userId)
    {
        boolean exists = userRepository.existsById(userId);

        if (!exists)
        {
            throw new UserNotFoundException("User with id " + userId + "does not exist.");
        }

    }


}
