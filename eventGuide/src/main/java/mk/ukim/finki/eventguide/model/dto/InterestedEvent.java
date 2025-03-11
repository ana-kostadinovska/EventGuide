package mk.ukim.finki.eventguide.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InterestedEvent {
    private Long id;
    private String name;
    private String artist;
    private LocalDate date;

}

