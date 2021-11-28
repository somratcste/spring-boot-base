package info.somrat.rest.repository;

import info.somrat.rest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);

     @Query(value = "select u.email, u.username, u.permissions from users u where u.username = ?1", nativeQuery = true)
     Tuple getUserByUsername(String username);

    @Query(value = "SELECT r.name FROM users u " +
            "join role_user ru on u.id =  ru.user_id " +
            "join roles r on r.id = ru.role_id " +
            "WHERE u.username = ?1", nativeQuery = true)
    List<String> getRoleByUsername(String username);
}
