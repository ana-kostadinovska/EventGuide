package mk.ukim.finki.eventguide.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Event> events;

    public User() {
    }

    public User(String username, String name, String surname, List<Event> events) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.events = events;
    }
}
