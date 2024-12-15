package mk.ukim.finki.eventguide.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @ManyToOne
    private Local local;

    @ManyToOne
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
    }

}
