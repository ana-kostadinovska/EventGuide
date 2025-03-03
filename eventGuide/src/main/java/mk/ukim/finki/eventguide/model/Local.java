package mk.ukim.finki.eventguide.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "locals")
public class Local {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String location;

    private String workingHours;

    private String contact_number;

    private String website;

    @Enumerated(EnumType.STRING)
    private LocalType type;

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Event> events;

    public Local() {
    }

    public Local(String name, String location, String workingHours, String contact_number, String website, LocalType type, List<Event> events) {
        this.name = name;
        this.location = location;
        this.workingHours = workingHours;
        this.contact_number = contact_number;
        this.website = website;
        this.type = type;
        this.events = events;
    }
}
