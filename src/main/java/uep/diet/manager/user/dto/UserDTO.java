package uep.diet.manager.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import uep.diet.manager.day.domain.data.Day;

import java.util.List;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
@Data
public class UserDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Integer kcalDayLimit;
    @JsonIgnore
    private List<Day> days;
    private Boolean enabled;

}
