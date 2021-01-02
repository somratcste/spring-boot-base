package info.somrat.rest.repository;

import info.somrat.rest.enums.ERole;
import info.somrat.rest.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(ERole name);
}
