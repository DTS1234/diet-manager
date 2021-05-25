package uep.diet.manager.user.domain;

import lombok.Data;
import uep.diet.manager.day.domain.Day;

import javax.persistence.*;
import java.util.List;

/**
 * @author akazmierczak
 * @date 18.04.2021
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    private String email;
    private String password;

    @OneToMany // one direction only
    private List<Day> days;

    private Integer dayLimit;

}
