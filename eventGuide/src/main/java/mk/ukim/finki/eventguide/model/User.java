package mk.ukim.finki.eventguide.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String sub;
    private String username;

    private String name;

    private String surname;

    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_event_interest",  // Join table name
            joinColumns = @JoinColumn(name = "user_id"),  // FK for User
            inverseJoinColumns = @JoinColumn(name = "event_id")  // FK for Event
    )
    @JsonManagedReference
    private Set<Event> interest = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Event> events;

    public User() {
    }

    public User(String sub, String username, String name, String surname, String email, List<Event> events) {
        this.sub = sub;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
