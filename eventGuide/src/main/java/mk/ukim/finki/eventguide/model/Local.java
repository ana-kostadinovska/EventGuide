package mk.ukim.finki.eventguide.model;

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

    @ElementCollection
    @CollectionTable(name = "local_work_times", joinColumns = @JoinColumn(name = "local_id"))
    private List<WorkTime> workTimes;

    private int contact_number;

    @Enumerated(EnumType.STRING)
    private LocalType type;

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    public Local() {
    }

    public Local(String name, String location, List<WorkTime> workTimes, int contact_number, LocalType type, List<Event> events) {
        this.name = name;
        this.location = location;
        this.workTimes = workTimes;
        this.contact_number = contact_number;
        this.type = type;
        this.events = events;
    }
}
