package mk.ukim.finki.eventguide.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String name;

    private String surname;

    private String email;

    public User() {
    }

    public User(String username, String name, String surname, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
