package uep.diet.manager.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uep.diet.manager.user.domain.UserService;
import uep.diet.manager.user.dto.UserDTO;
import uep.diet.manager.user.dto.UserDTOList;
import uep.diet.manager.user.dto.UserDaysDTO;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("users/")
public class UserController {

    private final UserService userService;

    @GetMapping("{username}/username")
    public UserDTO getUserByUserName(@PathVariable String username)
    {
        return userService.getByUsername(username);
    }

    @GetMapping("{id}")
    public UserDTO getUserId(@PathVariable Long id)
    {
        return userService.getById(id);
    }

    @GetMapping("all")
    public UserDTOList getAllUsers(){
        return userService.getAllUsers();
    }

    /** Returns user id and username with list of all days registered for this particular user. */
    @GetMapping("{id}/days")
    public UserDaysDTO getUserDays(@PathVariable Long id) {
        return userService.getUserWithDays(id);
    }

    @PutMapping("{id}/limit/{newLimit}")
    @Operation(description = "Update kcal limit by passing user id and new limit as path variables.")
    public UserDTO updateKcalLimit(@PathVariable Long id, @PathVariable Integer newLimit) {
        return userService.updateDayLimit(id, newLimit);
    }


}
