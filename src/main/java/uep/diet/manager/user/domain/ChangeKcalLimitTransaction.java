package uep.diet.manager.user.domain;

import uep.diet.manager.user.dto.UserDTO;
import uep.diet.manager.user.dto.UserMapper;

/**
 * @date 25.05.2021
 */
class ChangeKcalLimitTransaction {

    private final UserRepository userRepository;

    public ChangeKcalLimitTransaction(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute(Long id, Integer newDayLimit) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setDayLimit(newDayLimit);
        return UserMapper.toDTO(userRepository.save(user));
    }

}
