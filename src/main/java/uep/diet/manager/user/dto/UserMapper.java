package uep.diet.manager.user.dto;

import uep.diet.manager.user.domain.User;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
public class UserMapper {

    private UserMapper(){}

    public static UserDTO toDTO(User user) {

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUsername();
        String email = user.getEmail();
        Long id = user.getUserId();
        Integer kcalLimit = user.getDayLimit();
        Boolean enabled = user.getEnabled();

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setUserId(id);
        userDTO.setUsername(userName);
        userDTO.setKcalDayLimit(kcalLimit);
        userDTO.setEnabled(enabled);

        return userDTO;
    }
}
