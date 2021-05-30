package uep.diet.manager.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uep.diet.manager.ingredient.domain.Ingredient;
import uep.diet.manager.ingredient.domain.IngredientRepository;
import uep.diet.manager.user.domain.User;
import uep.diet.manager.user.domain.UserRepository;

import java.util.List;

/**
 * @date 29.05.2021
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IngredientRepository ingredientRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Ingredient> all = ingredientRepository.findAll();


        if (!userRepository.existsById(1L)){
            User user = new User();

            user.setUserId(1L);
            user.setDayLimit(2700);
            user.setEmail("adminuser@gmail.com");
            user.setPassword(this.passwordEncoder.encode("pass123"));
            user.setUsername("adminUser");
            user.addAuthority("ADMIN");
            userRepository.save(user);

            log.info("SAVED admin user !");
        }

        if (!userRepository.existsById(2L)){
            User user = new User();

            user.setUserId(2L);
            user.setDayLimit(2830);
            user.setEmail("normaluser@gmail.com");
            user.setPassword(this.passwordEncoder.encode("pass123"));
            user.setUsername("normalUser");
            user.addAuthority("NORMAL");
            userRepository.save(user);

            log.info("SAVED normal user !");
        }

        if (!userRepository.existsById(3L)){
            User user = new User();

            user.setUserId(3L);
            user.setDayLimit(2000);
            user.setEmail("manageruser@gmail.com");
            user.setPassword(this.passwordEncoder.encode("pass123"));
            user.setUsername("managerUser");
            user.addAuthority("MANAGER");
            userRepository.save(user);

            log.info("SAVED manager user !");
        }

    }

    private int findFirstCommaIndex(String string)
    {
        return string.indexOf(',') == -1 ? string.length()-1 : string.indexOf(',');
    }

}
