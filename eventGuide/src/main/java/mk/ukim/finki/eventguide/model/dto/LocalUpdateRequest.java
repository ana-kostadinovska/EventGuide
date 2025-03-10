package mk.ukim.finki.eventguide.model.dto;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.eventguide.model.LocalType;

@Getter
@Setter
public class LocalUpdateRequest {
    private String name;
    private String location;
    private String workingHours;
    private String contactNumber;
    private String website;
    private String image;
    private LocalType type;
}
