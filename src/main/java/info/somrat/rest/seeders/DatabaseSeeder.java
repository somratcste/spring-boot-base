package info.somrat.rest.seeders;

import info.somrat.rest.enums.ERole;
import info.somrat.rest.models.Role;
import info.somrat.rest.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Autowired
    RoleRepository roleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        logger.info("Run Seeder --------------------------");
        seedRolesTable();
        logger.info("End Seeder --------------------------");
    }

    private void seedRolesTable() {
        roleRepository.deleteAll();
        logger.info("Start role seeding ---------- " + roleRepository.count());
        Role admin = new Role(ERole.ROLE_ADMIN);
        Role moderator = new Role(ERole.ROLE_MODERATOR);
        Role user = new Role(ERole.ROLE_USER);
        roleRepository.saveAll(Arrays.asList(admin, moderator, user));
        logger.info("End role seeding ---------- " + roleRepository.count());
    }
}
