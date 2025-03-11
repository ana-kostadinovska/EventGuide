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
    private String referenceUrl; // ✅ New field for event source URL

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    @JsonIgnoreProperties("events")
    private Local local;

    @ManyToMany(mappedBy = "interest")
    @JsonBackReference
    private Set<User> interestedUsers = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"interest", "events", "roles"})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private EventRequest.RequestStatus status = EventRequest.RequestStatus.PENDING;

    public Event() {
    }

    public Event(String name, String artist, String description, LocalDate date, LocalTime time, String referenceUrl, Local local, User user) {
        this.name = name;
        this.artist = artist;
        this.description = description;
        this.date = date;
        this.time = time;
        this.local = local;
        this.user = user;
        this.interested = 0;
        this.referenceUrl = referenceUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id != null && id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
