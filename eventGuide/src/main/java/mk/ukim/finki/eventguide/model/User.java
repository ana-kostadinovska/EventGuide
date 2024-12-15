package mk.ukim.finki.eventguide.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Event> events;

    public User() {
    }

    public User(String username, String name, String surname, String email, List<Event> events) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.events = events;
    }
}
