package info.somrat.seed;

import info.somrat.entity.User;
import info.somrat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SeedData implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // delete all first
        this.userRepository.deleteAll();

        // Create Users
        User user = new User("user", passwordEncoder.encode("user123"), "USER", "");
        User admin = new User("admin", passwordEncoder.encode("nazmul123"), "ADMIN", "ACCESS_TEST1,ACCESS_TEST2");
        User manager = new User("manager", passwordEncoder.encode("nazmul123"), "MANAGER", "ACCESS_TEST1");

        List<User> users = Arrays.asList(user, admin, manager);
        this.userRepository.saveAll(users);
    }
}
