package uep.diet.manager.user.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author akazmierczak
 * @date 20.04.2021
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
