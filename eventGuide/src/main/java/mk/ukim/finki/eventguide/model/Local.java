package mk.ukim.finki.eventguide.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
public class Local {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String location;

    private LocalTime work_from;

    private LocalTime work_to;

    private int contact_number;

    @Enumerated(EnumType.STRING)
    private LocalType type;

    public Local() {
    }

    public Local(String name, String location, LocalTime work_from, LocalTime work_to, int contact_number, LocalType type) {
        this.name = name;
        this.location = location;
        this.work_from = work_from;
        this.work_to = work_to;
        this.contact_number = contact_number;
        this.type = type;
    }
}
