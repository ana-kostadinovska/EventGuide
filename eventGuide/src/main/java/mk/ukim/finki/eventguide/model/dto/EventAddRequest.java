package mk.ukim.finki.eventguide.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventAddRequest {
    public String name;
    public String artist;
    public String description;
    public LocalDate date;
    public LocalTime time;
    public Long local_id;
}

