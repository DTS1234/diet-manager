package uep.diet.manager.day;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uep.diet.manager.day.domain.service.DayService;
import uep.diet.manager.day.dto.DayDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

/**
 * @author akazmierczak
 * @date 18.04.2021
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class DayController {

    private final DayService dayService;

    @PostMapping(value = "/user/{userId}/day", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Adds date record for user with given id, user can have only one day with specific date.")
    public DayDTO createDayForUser(@PathVariable Long userId,
                                    @RequestBody
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                            description = "Only paste date here.",
                                            required = true,
                                            content = @Content(
                                            schema = @Schema(implementation = DayDTO.class),
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            examples = {
                                                    @ExampleObject(value = "{\"date\": \"2021-10-10\"}")
                                            }
                                            ))
                                            DayDTO dayDTO) {
        return dayService.createDayForUser(userId, dayDTO);
    }

    @PostMapping(value = "/user/{userId}/day/{dayId}/meal/{mealId}/deprecated")
    @Operation(description = "Adds meal to user's day.")
    public DayDTO addMealToUsersDay(@PathVariable Long userId,
                                    @PathVariable Long dayId,
                                    @PathVariable Long mealId) {
        return dayService.addMealToUsersDay(userId, dayId, mealId);
    }

    @DeleteMapping(value = "/user/{userId}/day/{dayId}/meal/{mealId}")
    @Operation(description = "Removes meal from users day record.")
    public DayDTO removeMealFromUsersDay(@PathVariable Long userId,
                                         @PathVariable Long dayId,
                                         @PathVariable Long mealId)
    {
        return dayService.removeMealFromUsersDay(userId, dayId, mealId);
    }

    @PostMapping(value = "/user/{userId}/day/{date}/meal/{mealId}")
    @Operation(description = "Adds meal to user's day.")
    public DayDTO addMealToUsersDay(@PathVariable Long userId,
                                    @PathVariable String date,
                                    @PathVariable Long mealId) {

        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMAN);
        TemporalAccessor parse = simpleDateFormat.parse(date);
        LocalDate localDate = LocalDate.from(parse);

        return dayService.addMealToUsersDay(userId, localDate, mealId);
    }

}
