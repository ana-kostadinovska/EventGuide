package mk.ukim.finki.eventguide.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event_request")
public class EventRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String artist;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private Integer interested = 0;
    private String referenceUrl;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User createdBy;

    public EventRequest(String name, String artist, String description, LocalDate date, LocalTime time, String referenceUrl, Local local, User createdBy) {
        this.name = name;
        this.artist = artist;
        this.description = description;
        this.date = date;
        this.time = time;
        this.local = local;
        this.referenceUrl = referenceUrl;
        this.createdBy = createdBy;
        this.status = RequestStatus.PENDING;
    }

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED, DISCARDED;
    }
}
