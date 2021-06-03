package uep.diet.manager.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uep.diet.manager.user.domain.data.User;
import uep.diet.manager.user.domain.data.UserRepository;

/**
 * @date 29.05.2021
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {

    public static final String PASSWORD = "pass123";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepository.existsById(1L)){
            User user = new User();

            user.setUserId(1L);
            user.setDayLimit(2700);
            user.setEmail("adminuser@gmail.com");
            user.setPassword(this.passwordEncoder.encode(PASSWORD));
            user.setUsername("adminUser");
            user.addAuthority("ADMIN");
            user.setFirstName("Admin");
            user.setLastName("Someone");
            userRepository.save(user);

            log.info("SAVED admin user !");
        }

        if (!userRepository.existsById(2L)){
            User user = new User();

            user.setUserId(2L);
            user.setDayLimit(2830);
            user.setEmail("normaluser@gmail.com");
            user.setPassword(this.passwordEncoder.encode(PASSWORD));
            user.setUsername("normalUser");
            user.addAuthority("NORMAL");
            user.setFirstName("Normal");
            user.setLastName("Someguy");
            userRepository.save(user);

            log.info("SAVED normal user !");
        }

        if (!userRepository.existsById(3L)){
            User user = new User();

            user.setUserId(3L);
            user.setDayLimit(2000);
            user.setEmail("manageruser@gmail.com");
            user.setPassword(this.passwordEncoder.encode(PASSWORD));
            user.setUsername("managerUser");
            user.addAuthority("MANAGER");
            user.setFirstName("Manager");
            user.setLastName("SomeOne");
            userRepository.save(user);

            log.info("SAVED manager user !");
        }

    }
}
