package uep.diet.manager.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uep.diet.manager.day.dto.DayDTO;

import java.util.List;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDaysDTO {
    private Long id;
    private String username;
    private List<DayDTO> days;
}
