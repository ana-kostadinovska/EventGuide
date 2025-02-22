package mk.ukim.finki.eventguide.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String artist;

    private String description;

    private LocalDate date;

    private LocalTime time;
    private Integer interested;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    @JsonIgnoreProperties("events")
    private Local local;

    @ManyToMany(mappedBy = "interest")  // Reference to User's interest field
    @JsonBackReference
    private Set<User> interestedUsers = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Event() {
    }

    public Event(String name, String artist, String description, LocalDate date, LocalTime time, Local local) {
        this.name = name;
        this.artist = artist;
        this.description = description;
        this.date = date;
        this.time = time;
        this.local = local;
        interested=0;
    }
}
