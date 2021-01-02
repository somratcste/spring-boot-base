package info.somrat.rest.service;

import info.somrat.rest.enums.ERole;
import info.somrat.rest.models.Role;
import info.somrat.rest.models.User;
import info.somrat.rest.repository.RoleRepository;
import info.somrat.rest.repository.UserRepository;
import info.somrat.rest.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Create a new user with roles
     * @param request
     * @return
     */
    @Transactional
    public User save(SignUpRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        user.setRoles(getRoles(request.getRoles()));
        userRepository.save(user);
        return user;
    }

    /**
     * Get user roles
     * @param requestRoles
     * @return
     */
    private Set<Role> getRoles(Set<String> requestRoles) {
        Set<Role> roles = new HashSet<>();
        if (requestRoles == null) {
            roles.add(roleRepository.findByName(ERole.ROLE_USER));
        } else {
            requestRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleRepository.findByName(ERole.ROLE_ADMIN));
                        break;
                    case "mod":
                        roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR));
                        break;
                    case "user":
                        roles.add(roleRepository.findByName(ERole.ROLE_USER));
                        break;
                }
            });
        }
        return roles;
    }

    /**
     * Get user by username
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
