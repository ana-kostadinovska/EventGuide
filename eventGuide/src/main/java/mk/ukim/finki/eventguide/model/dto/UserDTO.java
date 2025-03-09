package mk.ukim.finki.eventguide.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String sub;
    private String username;
    private String email;
    private List<String> roles;
}
