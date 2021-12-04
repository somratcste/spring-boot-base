package info.somrat.repository;

import info.somrat.rest.enums.ERole;
import info.somrat.rest.models.Role;
import info.somrat.rest.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("findByName")
    public void findByName() {
        Role role = roleRepository.findByName(ERole.ROLE_USER);
        assertEquals(ERole.ROLE_USER, role.getName());
    }
}
