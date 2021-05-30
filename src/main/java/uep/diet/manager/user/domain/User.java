package uep.diet.manager.user.domain;

import lombok.Data;
import uep.diet.manager.day.domain.Day;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akazmierczak
 * @date 18.04.2021
 */
@Data
@Entity(name = "users")
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
    private Integer dayLimit;
    private Boolean enabled = true;

    @OneToMany // one direction only
    private List<Day> days;

    @OneToMany(cascade = CascadeType.ALL)
    List<UserAuthority> authorities = new ArrayList<>();

    public void addAuthority(String authority) {
        this.authorities.add(new UserAuthority(this, authority));
    }
}
