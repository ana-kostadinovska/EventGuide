package mk.ukim.finki.eventguide.model.dto;

import java.util.List;
import java.util.Objects;

public class CreateLocalDto {
    public String Name;
    public String Address;
    public String PrimaryType;
    public List<String> Types;
    public String PhoneNumber;
    public List<String> OpeningHours;
    public String MapsLink;
    public String LocalWebsite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateLocalDto that = (CreateLocalDto) o;
        return Objects.equals(Name, that.Name) && Objects.equals(Address, that.Address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Address);
    }
}

