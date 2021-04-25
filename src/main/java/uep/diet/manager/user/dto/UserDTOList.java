package uep.diet.manager.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOList {
    private List<UserDTO> users;
}
