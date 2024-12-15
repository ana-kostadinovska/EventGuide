package mk.ukim.finki.eventguide.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class WorkTime {

    private String day;

    private String startTime;

    private String endTime;

    public WorkTime() {

    }

    public WorkTime(String day, String startTime, String endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
