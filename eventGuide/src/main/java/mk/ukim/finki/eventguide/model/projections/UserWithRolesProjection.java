package mk.ukim.finki.eventguide.model.projections;

public interface UserWithRolesProjection {
    Long getUserId();
    String getSub();
    String getUsername();
    String getName();
    String getSurname();
    String getEmail();
    Long getRoleId();
    String getRoleName();
}

