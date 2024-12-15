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

    private String workingHours;

    private int contact_number;

    @Enumerated(EnumType.STRING)
    private LocalType type;

    public Local() {
    }

    public Local(String name, String location, String workingHours, int contact_number, LocalType type) {
        this.name = name;
        this.location = location;
        this.workingHours = workingHours;
        this.contact_number = contact_number;
        this.type = type;
    }
}
