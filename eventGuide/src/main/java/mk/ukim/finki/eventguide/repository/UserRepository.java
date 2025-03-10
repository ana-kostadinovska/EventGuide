package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.model.dto.UserDTO;
import mk.ukim.finki.eventguide.model.projections.UserWithRolesProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findBySub(String sub);


    Optional<User> findByEmail(String email);

    @Query(value = """
        SELECT 
            u.id AS userId, u.sub AS sub, u.username AS username, 
            u.name AS name, u.surname AS surname, u.email AS email, 
            r.id AS roleId, r.name AS roleName
        FROM app_users u
        LEFT JOIN user_roles ur ON u.id = ur.user_id
        LEFT JOIN roles r ON ur.role_id = r.id
        WHERE u.sub = :sub
    """, nativeQuery = true)
    List<UserWithRolesProjection> findBySubWithRolesNative(@Param("sub") String sub);

}
